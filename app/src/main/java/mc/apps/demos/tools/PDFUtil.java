package mc.apps.demos.tools;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Class used to generate PDF for the given Views.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class PDFUtil {
    private static final String TAG = "tests";
    public static final double PDF_PAGE_WIDTH = 8.3 * 72;
    public static final double PDF_PAGE_HEIGHT = 11.7 * 72 * 2;
    private static final String PDFS_DIRECTORY_NAME = "pdfs";

    // public static final double PDF_PAGE_WIDTH_INCH = 8.3;
    // public static final double PDF_PAGE_HEIGHT_INCH = 11.7;

    private static PDFUtil sInstance;
    private PDFUtil() {

    }
    public static PDFUtil getInstance() {
        if (sInstance == null) {
            sInstance = new PDFUtil();
        }
        return sInstance;
    }

    private Context context;
    public final void generatePDF(Context context, final List<View> contentViews, final String fileName, final PDFUtilListener listener) {
        this.context = context;

        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            Log.i(TAG, "Generate PDF available for your android version..");
            new GeneratePDFAsync(contentViews, fileName, listener).execute();
        } else {
            // Before Kitkat
            Log.i(TAG, "Generate PDF is not available for your android version.");
            listener.pdfGenerationFailure(new APINotSupportedException("Generate PDF is not available for your android version."));
        }

    }

    public interface PDFUtilListener {
        void pdfGenerationSuccess(File savedPDFFile);
        void pdfGenerationFailure(final Exception exception);
    }
    private class GeneratePDFAsync extends AsyncTask<Void, Void, File> {
        private List<View> contentViews;
        private String fileName;
        private PDFUtilListener listener = null;

        public GeneratePDFAsync(final List<View> contentViews, final String fileName, final PDFUtilListener listener) {
            Log.i(TAG, "GeneratePDFAsync..");

            this.contentViews = contentViews;
            this.fileName = fileName;
            this.listener = listener;
        }
        @Override
        protected File doInBackground(Void... params) {
            try {
                Log.i(TAG, "GeneratePDFAsync..doInBackground.. Create PDF Document..");
                PdfDocument pdfDocument = new PdfDocument();
                Log.i(TAG, "GeneratePDFAsync..doInBackground.. Write content to PDFDocument..");
                writePDFDocument(pdfDocument);

                Log.i(TAG, "GeneratePDFAsync..doInBackground.. Save document to file..");
                return savePDFDocumentToStorage(pdfDocument);
            } catch (Exception exception) {
                Log.e(TAG, exception.getMessage());
                return null;
            }
        }
        @Override
        protected void onPostExecute(File savedPDFFile) {
            super.onPostExecute(savedPDFFile);
            Log.i(TAG, "GeneratePDFAsync..onPostExecute..");
            if (savedPDFFile != null) {
                Log.i(TAG, "GeneratePDFAsync..onPostExecute..Send Success callback.");
                listener.pdfGenerationSuccess(savedPDFFile);
            } else {
                Log.i(TAG, "GeneratePDFAsync..onPostExecute..Send Error callback.");
                //listener.pdfGenerationFailure(mException);
            }
        }


        private void writePDFDocument(final PdfDocument pdfDocument) {
            Log.i(TAG, "GeneratePDFAsync..writePDFDocument..");

            for (int i = 0; i < contentViews.size(); i++) {
                View contentView = contentViews.get(i);

               /* int VIEW_WIDTH = contentView.getWidth();
                int VIEW_HEIGHT = contentView.getHeight();

                Log.i(TAG, "writePDFDocument: "+VIEW_WIDTH+" "+VIEW_HEIGHT);*/

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder((int) PDF_PAGE_WIDTH, (int) PDF_PAGE_HEIGHT, i + 1).create();
               // PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(VIEW_WIDTH, VIEW_HEIGHT, i + 1).create();

                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas pageCanvas = page.getCanvas();
                pageCanvas.scale(1f, 1f);

                int pageWidth = pageCanvas.getWidth();
                int pageHeight = pageCanvas.getHeight();

                int measureWidth = View.MeasureSpec.makeMeasureSpec(pageWidth, View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(pageHeight, View.MeasureSpec.EXACTLY);

                contentView.measure(measureWidth, measuredHeight);
                contentView.layout(0, 0, pageWidth, pageHeight);
                contentView.draw(pageCanvas);

                Log.i(TAG, "GeneratePDFAsync..writePDFDocument..finish the page");
                pdfDocument.finishPage(page);

            }
        }
        private File savePDFDocumentToStorage(final PdfDocument pdfDocument) throws IOException {

            Log.i(TAG, "GeneratePDFAsync..savePDFDocumentToStorage..");
            FileOutputStream fos = null;

            // Create file.
            File files_directory = context.getExternalFilesDir(PDFS_DIRECTORY_NAME);
            File pdfFile = new File(files_directory, "/" + fileName + ".pdf");

            /*//Create parent directories
            File parentFile = pdfFile.getParentFile();
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                throw new IllegalStateException("Couldn't create directory: " + parentFile);
            }*/
            boolean fileExists = pdfFile.exists();
            if (fileExists) {
                fileExists = !pdfFile.delete();
            }
            try {
                if (!fileExists) {
                    // Create New File.
                    fileExists = pdfFile.createNewFile();
                }

                if (fileExists) {
                    fos = new FileOutputStream(pdfFile);
                    pdfDocument.writeTo(fos);
                    fos.close();
                    pdfDocument.close();
                }
                Log.i(TAG, "GeneratePDFAsync..savePDFDocumentToStorage..return "+pdfFile);
                return pdfFile;
            } catch (IOException exception) {
                exception.printStackTrace();
                if (fos != null) {
                    fos.close();
                }
                throw exception;
            }
        }
    }

    /**
     * APINotSupportedException will be thrown If the device doesn't support PDF methods.
     */
    private static class APINotSupportedException extends Exception {
        // mErrorMessage.
        private String mErrorMessage;

        /**
         * Constructor.
         *
         * @param errorMessage Error Message.
         */
        public APINotSupportedException(final String errorMessage) {
            this.mErrorMessage = errorMessage;
        }

        /**
         * To String.
         *
         * @return error message as a string.
         */
        @Override
        public String toString() {
            return "APINotSupportedException{" +
                    "mErrorMessage='" + mErrorMessage + '\'' +
                    '}';
        }
    }

    /**
     * Convert PDF to bitmap, only works on devices above LOLLIPOP
     *
     * @param pdfFile pdf file
     * @return list of bitmap of every page
     * @throws Exception
     */
    public static ArrayList<Bitmap> pdfToBitmap(File pdfFile) throws Exception, IllegalStateException {
        if (pdfFile == null || pdfFile.exists() == false) {
            throw new IllegalStateException("");
        }
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            throw new Exception("PDF preview image cannot be generated in this device");
        }
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        try {
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY));

            Bitmap bitmap;
            final int pageCount = renderer.getPageCount();
            for (int i = 0; i < pageCount; i++) {
                PdfRenderer.Page page = renderer.openPage(i);


                int width = page.getWidth();
                int height = page.getHeight();

                /* FOR HIGHER QUALITY IMAGES, USE:
                int width = context.getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
                int height = context.getResources().getDisplayMetrics().densityDpi / 72 * page.getHeight();
                */

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                bitmaps.add(bitmap);

                // close the page
                page.close();

            }

            // close the renderer
            renderer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmaps;

    }

    public static void OpenPDF(Context context, File file) {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "PDF Reader not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
