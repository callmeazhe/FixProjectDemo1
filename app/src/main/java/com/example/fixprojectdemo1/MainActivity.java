package com.example.fixprojectdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public void doCalculator(View view) {
        Calculator calculator = new Calculator();
        calculator.calculator();

    }
//81515549   60553688

    public void doFix(View view) {
        File file = new File(Environment.getExternalStorageDirectory(),
                "fix.dex");
        DexManager dexManager = new DexManager(this);
        dexManager.load(file);

    }
}