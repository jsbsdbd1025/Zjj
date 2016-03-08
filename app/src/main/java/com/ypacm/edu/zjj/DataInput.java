package com.ypacm.edu.zjj;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by DB on 2016/3/2.
 */
public class DataInput extends AppCompatActivity {

    JsonUtils jsonUtils = new JsonUtils();
    JSONObject jsonObject;
    double tempValue;
    double controlValue1;
    double A;
    double B;
    double upRate;
    double upKouFen;
    double downRate;
    double downKouFen;

    double businessMax;
    double businessPara;

    double seniorityMax;
    double seniorityPara;

    double technicalMax;
    double technicalPara;

    double zixinMax;
    double zixinPara;

    double sum;
    double average1, average2;
    double controlValue2;
    double measure1, measure2;
    int resquest_Code = 1;
    public static DataInput myThis;
    EditText editText;
    int customNum = 0;
    List<ItemBean> itemBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        myThis = this;
        editText = (EditText) findViewById(R.id.et_number);
        editText.setText("" + customNum);
    }


    public void onClick1(View v) {
        editText = (EditText) findViewById(R.id.et_number);
        customNum = Integer.parseInt(editText.getText().toString());
        itemBeanList.clear();
        for (int i = 1; i <= customNum; i++) {
            itemBeanList.add(new ItemBean(i, "第" + i + "家公司", (double) i, (double) i, i, 0, 0, 0, 0, 0));
        }
        ListView listView = (ListView) findViewById(R.id.lv_main);
        listView.setAdapter(new MyAdapter(this, itemBeanList));
    }

    public void onActivityResult(int resquestCode, int resultCode, Intent data) {
        if (resquestCode == resquest_Code) {
            if (resultCode == RESULT_OK) {
                Bundle bundle;
                bundle = data.getExtras();
                int id = bundle.getInt("id");
                //   Log.i("aaaa", String.valueOf(bundle.getInt("id")));
                double temp = bundle.getDouble("technical");
                //   Log.i("aaaa", "" + temp);
                Toast.makeText(this, "" + temp, Toast.LENGTH_LONG).show();
                itemBeanList.get(id).item_price = bundle.getDouble("price");
                itemBeanList.get(id).item_technical = bundle.getDouble("technical");
                itemBeanList.get(id).item_zixin = bundle.getDouble("zixin");
                itemBeanList.get(id).item_seniority = bundle.getDouble("seniority");
                ListView listView = (ListView) findViewById(R.id.lv_main);
                listView.setAdapter(new MyAdapter(this, itemBeanList));
            }
        }
    }

    public void para(View v) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void calc(View v) {
        readJsonData();
        sum = 0;
        for (int i = 0; i < customNum; i++) {
            sum += itemBeanList.get(i).item_price;
        }
        average1 = sum / customNum;
        Log.i("aaaa", "average1:" + average1);
        average2 = average1 - tempValue;
        Log.i("aaaa", "average2:" + average2);
        controlValue2 = controlValue1 - tempValue;
        Log.i("aaaa", "controlValue2:" + controlValue2);
        measure1 = average2 * B + controlValue2 * (1 + A) * (1 - B);
        Log.i("aaaa", "measure1:" + measure1);
        measure2 = measure1 + tempValue;
        Log.i("aaaa", "measure2:" + measure2);
        ItemBean t;
        for (int i = 0; i < customNum; i++) {
            t = itemBeanList.get(i);
            t.item_down = (1 - (t.item_price - tempValue) / controlValue2) * 100;
            Log.i("aaaa", "t.item_id+:" + "item_down:" + t.item_down);
            t.item_business = t.item_price > measure2 ? 100 - (t.item_price - measure2) / measure2 / upRate * upKouFen :
                    100 + (t.item_price - measure2) / measure2 / downRate * downKouFen;
            Log.i("aaaa", "t.item_id+:" + "item_business:" + t.item_business);

            t.item_score = 0;
            if (businessMax != 0)
                t.item_score += (t.item_business * businessMax / 100) * businessPara;
            if (seniorityMax != 0)
                t.item_score += (t.item_seniority * seniorityMax / 100) * seniorityPara;
            if (technicalMax != 0)
                t.item_score += (t.item_technical * technicalMax / 100) * technicalPara;
            if (zixinMax != 0)
                t.item_score += (t.item_zixin * zixinMax / 100) * zixinPara;
            Log.i("aaaa", "t.item_id:" + i + "item_score:" + t.item_score);
        }
        Collections.sort(itemBeanList, new SortByRank());
        for (int i = 0; i < customNum; i++) {
            t = itemBeanList.get(i);
            t.item_rank = i + 1;
        }
        ListView listView = (ListView) findViewById(R.id.lv_main);
        listView.setAdapter(new MyAdapter(this, itemBeanList));
    }

    class SortByRank implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            ItemBean b1 = (ItemBean) lhs;
            ItemBean b2 = (ItemBean) rhs;
            if (b1.item_score > b2.item_score)
                return -1;
            else
                return 1;
        }
    }

    public void readJsonData() {
        try {
            jsonObject = new JSONObject(jsonUtils.readJson("para"));
            tempValue = Double.parseDouble(jsonObject.get("tempValue").toString());
            controlValue1 = Double.parseDouble(jsonObject.get("controlValue1").toString());
            A = Double.parseDouble(jsonObject.get("A").toString());
            B = Double.parseDouble(jsonObject.get("B").toString());
            upRate = Double.parseDouble(jsonObject.get("upRate").toString());
            upKouFen = Double.parseDouble(jsonObject.get("upKouFen").toString());
            downRate = Double.parseDouble(jsonObject.get("downRate").toString());
            downKouFen = Double.parseDouble(jsonObject.get("downKouFen").toString());
            businessMax = Double.parseDouble(jsonObject.get("businessMax").toString());
            businessPara = Double.parseDouble(jsonObject.get("businessPara").toString());
            seniorityMax = Double.parseDouble(jsonObject.get("seniorityMax").toString());
            seniorityPara = Double.parseDouble(jsonObject.get("seniorityPara").toString());
            technicalMax = Double.parseDouble(jsonObject.get("technicalMax").toString());
            technicalPara = Double.parseDouble(jsonObject.get("technicalPara").toString());
            zixinMax = Double.parseDouble(jsonObject.get("zixinMax").toString());
            zixinPara = Double.parseDouble(jsonObject.get("zixinPara").toString());

        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
        }
    }
}
