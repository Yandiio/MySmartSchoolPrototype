package penerbangroket.mysmartschoolprototype;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private final int oneSecond = 1000; //ms
    private final int imageViewChangeAt = 4; //sec
    private int imageViewCounter = 0; //sec
    private int radioCheckedNum = 0;
    RadioButton radButton1;
    RadioButton radButton2;
    RadioButton radButton3;
    ImageView imageView1;
    Thread thread1;

    private void init() {
        imageView1 = findViewById(R.id.imageView);
        radButton1 = findViewById(R.id.radioButton);
        radButton2 = findViewById(R.id.radioButton2);
        radButton3 = findViewById(R.id.radioButton3);
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (thread1.isAlive()) {
                        radioButtonChecked();
                    }
                    Thread.sleep(oneSecond);
                    imageViewCounter++;
                    Log.d("Counter", "ImageCounter Thread Exec true ");
                    if (imageViewCounter == imageViewChangeAt) {
                        imageViewCounter = 0;
                        radioCheckedNum++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void radioButtonChecked() {
        if (radButton1.isChecked()) {
            bitmapRescale();
            imageView1.setImageResource(R.drawable.example1);
        } else if (radButton2.isChecked()) {
            bitmapRescale();
            imageView1.setImageResource(R.drawable.example2);
        } else if (radButton3.isChecked()) {
            bitmapRescale();
            imageView1.setImageResource(R.drawable.example3);
        }
    }

    private void bitmapRescale() {
        Log.d("ImageSize","ImageSize : "+imageView1.getDrawable().getIntrinsicWidth());
        imageView1.setImageBitmap(decodeSampledBitmapFromResource(getResources(),
                R.id.imageView,
                100,
                100));
        Log.d("ImageSize","ImageSize : "+imageView1.getDrawable().getIntrinsicWidth());
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 0;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight &&
                    (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,option);

        option.inSampleSize = calculateInSampleSize(option,reqWidth,reqHeight);

        option.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,option);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bitmapRescale();
        thread1.start();
        radButton3.setChecked(true);
        radioButtonChecked();
    }
}