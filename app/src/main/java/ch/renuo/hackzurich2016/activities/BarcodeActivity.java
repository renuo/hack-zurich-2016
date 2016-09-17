package ch.renuo.hackzurich2016.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ch.renuo.hackzurich2016.R;
import ch.renuo.hackzurich2016.qrcode.QRcodeInterface;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Intent intent = getIntent();
        String householdId = intent.getStringExtra(getString(R.string.household_id));

        Bitmap bmp = null;
        try {
            bmp = QRcodeInterface.generateBitmap(householdId);

            ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backButtonClicked(View view){
        finish();
    }
}
