package mc.apps.demos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mc.apps.demos.tools.PDFUtil;

public class PdfGenerateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_generate);


        PdfGenerateActivity.verifyStoragePermissions(this);
    }
    public void generate(View v) {
        GeneratePDF();
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void GeneratePDF() {

        TextView details = findViewById(R.id.details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("tests", "generate: justify!");
            details.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        PDFUtil pdf = PDFUtil.getInstance();

        View view = getLayoutInflater().inflate(R.layout.activity_pdf_generate, null);
        List<View> views = new ArrayList<View>();
        views.add(view);

        String currentDate = new SimpleDateFormat("ddMMyyHHmm", Locale.getDefault()).format(new Date());
        Log.i("tests" , "onCreate: "+currentDate);
        String fileName = "rapport_"+ currentDate;

        //VM ignores the file URI exposure!
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        pdf.generatePDF(getApplicationContext(), views, fileName, new PDFUtil.PDFUtilListener() {
            @Override
            public void pdfGenerationSuccess(File savedPDFFile) {
                //Toast.makeText(PdfGenerateActivity.this, "pdf Generation Success! : "+savedPDFFile, Toast.LENGTH_SHORT).show();
                PDFUtil.OpenPDF(getApplicationContext(), savedPDFFile);
            }
            @Override
            public void pdfGenerationFailure(Exception exception) {
                Toast.makeText(PdfGenerateActivity.this, "pdf Generation Failure : "+exception, Toast.LENGTH_SHORT).show();
            }
        });
    }




}