package mc.apps.demos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mc.apps.demos.tools.PaintView;

public class SignatureActivity extends AppCompatActivity {
    private final static String TAG = "Main";
    private static final String SIGNATURES_DIRECTORY_NAME = "signatures";
    private PaintView mPaintView;
    private LinearLayout mLlCanvas;

    TextView RedDot,GreenDot,BlueDot;
    Button SaveBtn,ClearBtn ;
    String Front_Image="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        mLlCanvas = (LinearLayout)findViewById(R.id.llCanvas);
        mPaintView = new PaintView(this, null);
        mLlCanvas.addView(mPaintView, 0);
        mPaintView.requestFocus();

        SaveBtn= findViewById(R.id.btnSave);
        SaveBtn.setOnClickListener(v -> {
            savingFile();
            //Add_Front();
        });


        ClearBtn= findViewById(R.id.btnReset);
        ClearBtn.setOnClickListener(v -> mPaintView.clear());

        RedDot= findViewById(R.id.RedColor);
        RedDot.setOnClickListener(v -> mPaintView.setPathColor(Color.RED));

        GreenDot= findViewById(R.id.GreenColor);
        GreenDot.setOnClickListener(v -> mPaintView.setPathColor(Color.GREEN));

        BlueDot= findViewById(R.id.BlueColor);
        BlueDot.setOnClickListener(v -> mPaintView.setPathColor(Color.BLUE));
    }

    private void Add_Front() {
        //TODO
    }

    //saving drawed file to SD card
    private void savingFile() {
       /* File sdCard = Environment.getExternalStorageDirectory();
        File folder = new File(sdCard.getAbsolutePath() + "/MyFiles");*/

        String currentDate = new SimpleDateFormat("ddMMyyHHmm", Locale.getDefault()).format(new Date());
        String fileName = "signature_"+ currentDate;

        File files_directory = getApplicationContext().getExternalFilesDir(SIGNATURES_DIRECTORY_NAME);
        File file = new File(files_directory, "/" + fileName + ".png");

        boolean success = false;
       /* if (!folder.exists()) {
            success = folder.mkdirs();
            Log.i(TAG, " " + success);
        }

        File file = new File(folder, "BodyFront.png");*/

        if (!file.exists()) {
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(file);

            Bitmap well = mPaintView.getBitmap();
            Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Canvas now = new Canvas(save);
            now.drawRect(new Rect(0, 0, 320, 480), paint);
            now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight()), new Rect(0, 0, 320, 480), null);

            save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            BitMapToString(save);

            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Null error", Toast.LENGTH_SHORT).show();
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File error", Toast.LENGTH_SHORT).show();
        }
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Front_Image = Base64.encodeToString(b, Base64.DEFAULT);
        return Front_Image;
    }

    public void onClick(View view) {

    }
}