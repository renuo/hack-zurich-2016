package ch.renuo.hackzurich2016.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ch.renuo.hackzurich2016.R;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
    }

    public void backButtonClicked(View view){
        finish();
    }
}
