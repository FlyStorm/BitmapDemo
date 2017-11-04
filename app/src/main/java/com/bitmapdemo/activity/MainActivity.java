package com.bitmapdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button mBtn_load_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_load_bitmap = (Button) findViewById(R.id.btn_load_bitmap);

        //设置监听
        mBtn_load_bitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoadImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
