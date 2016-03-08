package com.ypacm.edu.zjj;

import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    JsonUtils jsonUtils = new JsonUtils();
    String sdCard = Environment.getExternalStorageDirectory().toString();
    JSONObject jsonObject;
    EditText tempValue;
    EditText controlValue1;
    EditText A;
    EditText B;
    EditText upRate;
    EditText upKouFen;
    EditText downRate;
    EditText downKouFen;

    EditText businessMax;
    EditText businessPara;

    EditText seniorityMax;
    EditText seniorityPara;

    EditText technicalMax;
    EditText technicalPara;

    EditText zixinMax;
    EditText zixinPara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
        tempValue = (EditText) findViewById(R.id.et_tempValue);
        controlValue1 = (EditText) findViewById(R.id.et_controlValue1);
        A = (EditText) findViewById(R.id.et_A);
        B = (EditText) findViewById(R.id.et_B);
        upRate = (EditText) findViewById(R.id.et_upRate);
        upKouFen = (EditText) findViewById(R.id.et_upKouFen);
        downRate = (EditText) findViewById(R.id.et_downRate);
        downKouFen = (EditText) findViewById(R.id.et_downKouFen);

        businessMax = (EditText) findViewById(R.id.et_businessMax);
        businessPara = (EditText) findViewById(R.id.et_businessPara);

        seniorityMax = (EditText) findViewById(R.id.et_seniorityMax);
        seniorityPara = (EditText) findViewById(R.id.et_seniorityPara);

        technicalMax = (EditText) findViewById(R.id.et_technicalMax);
        technicalPara = (EditText) findViewById(R.id.et_technicalPara);

        zixinMax = (EditText) findViewById(R.id.et_zixinMax);
        zixinPara = (EditText) findViewById(R.id.et_zixinPara);

        double freeSize = getSDFreeSize(android.os.Environment.getExternalStorageDirectory());
        Log.i("aaaa", "SDFreeSize:" + freeSize + "MB");
        File file = new File(sdCard, "para.json");
        Log.i("aaaa", sdCard);

        try {
            String temp = jsonUtils.readJson("para");
            jsonObject = new JSONObject(temp);
            jsonUtils.writeJson(jsonObject, "para");
        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
        }

        try {
            jsonObject = new JSONObject(jsonUtils.readJson("para"));
            tempValue.setText(jsonObject.get("tempValue").toString());
            controlValue1.setText(jsonObject.get("controlValue1").toString());
            A.setText(jsonObject.get("A").toString());
            B.setText(jsonObject.get("B").toString());
            upRate.setText(jsonObject.get("upRate").toString());
            upKouFen.setText(jsonObject.get("upKouFen").toString());
            downRate.setText(jsonObject.get("downRate").toString());
            downKouFen.setText(jsonObject.get("downKouFen").toString());

            businessMax.setText(jsonObject.get("businessMax").toString());
            businessPara.setText(jsonObject.get("businessPara").toString());
            seniorityMax.setText(jsonObject.get("seniorityMax").toString());
            seniorityPara.setText(jsonObject.get("seniorityPara").toString());
            technicalMax.setText(jsonObject.get("technicalMax").toString());
            technicalPara.setText(jsonObject.get("technicalPara").toString());
            zixinMax.setText(jsonObject.get("zixinMax").toString());
            zixinPara.setText(jsonObject.get("zixinPara").toString());
            Log.i("aaaa", "Json数据设置完成");
        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
        }

    }

    public void onClick(View v) {
        try {
            // 首先最外层是{}，是创建一个对象
            JSONObject para = new JSONObject();
            para.put("tempValue", tempValue.getText().toString());
            para.put("controlValue1", controlValue1.getText().toString());
            para.put("A", A.getText().toString());
            para.put("B", B.getText().toString());
            para.put("upRate", upRate.getText().toString());
            para.put("upKouFen", upKouFen.getText().toString());
            para.put("downRate", downRate.getText().toString());
            para.put("downKouFen", downKouFen.getText().toString());

            para.put("businessMax", businessMax.getText().toString());
            para.put("businessPara", businessPara.getText().toString());

            para.put("seniorityMax", seniorityMax.getText().toString());
            para.put("seniorityPara", seniorityPara.getText().toString());

            para.put("technicalMax", technicalMax.getText().toString());
            para.put("technicalPara", technicalPara.getText().toString());

            para.put("zixinMax", zixinMax.getText().toString());
            para.put("zixinPara", zixinPara.getText().toString());
            jsonUtils.writeJson(para, "para");
        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
        }

        finish();
//        Intent intent = new Intent(this, DataInput.class);
//        startActivity(intent);
    }

    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public double getSDFreeSize(File path) {

        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }
}
