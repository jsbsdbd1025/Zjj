package com.ypacm.edu.zjj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by DB on 2016/3/5.
 */
public class CustomDataInput extends AppCompatActivity {
    TextView id;
    EditText price;
    EditText technical;
    EditText zixin;
    EditText seniority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom);

        id = (TextView) findViewById(R.id.tv_id);
        price = (EditText) findViewById(R.id.et_price);
        technical = (EditText) findViewById(R.id.et_technical);
        zixin = (EditText) findViewById(R.id.et_zixin);
        seniority = (EditText) findViewById(R.id.et_seniority);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        int pos = bundle.getInt("id") + 1;
        id.setText("用户编号为：" + pos);
        DecimalFormat df = new DecimalFormat("#########.00");
        price.setText(String.valueOf(df.format(bundle.getDouble("price"))));
        technical.setText(String.valueOf(df.format(bundle.getDouble("technical"))));
        zixin.setText(String.valueOf(df.format(bundle.getDouble("zixin"))));
        seniority.setText(String.valueOf(df.format(bundle.getDouble("seniority"))));
    }

    public void onClick(View v) {

        price = (EditText) findViewById(R.id.et_price);
        technical = (EditText) findViewById(R.id.et_technical);
        zixin = (EditText) findViewById(R.id.et_zixin);
        seniority = (EditText) findViewById(R.id.et_seniority);
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putDouble("price", Double.parseDouble(price.getText().toString()));
        Log.i("aaaa", "" + Double.parseDouble(price.getText().toString()));
        bundle.putDouble("technical", Double.parseDouble(technical.getText().toString()));
        bundle.putDouble("zixin", Double.parseDouble(zixin.getText().toString()));
        bundle.putDouble("seniority", Double.parseDouble(seniority.getText().toString()));
        bundle.putInt("id", getIntent().getIntExtra("id", 0));
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        finish();
    }
}
