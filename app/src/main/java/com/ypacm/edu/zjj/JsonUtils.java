package com.ypacm.edu.zjj;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by tang on 14-3-1.
 */
public class JsonUtils {

    String sdCard = Environment.getExternalStorageDirectory().toString();
    String[] PARA = {"tempValue", "controlValue1", "A", "B", "upRate", "upKouFen", "downRate", "downKouFen",
            "businessMax", "businessPara", "seniorityMax", "seniorityPara", "technicalMax", "technicalPara",
            "zixinMax", "zixinPara"};
    JSONObject jsonObject;

    //从给定位置读取Json文件
    public String readJson(String name) {
        int flag = 0;
        //从给定位置获取文件
        File file = new File(sdCard, name + ".json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("aaaa", "Json新建成功");
        }
        Log.i("aaaa", file.toString());
        BufferedReader reader = null;
        //返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            reader = new BufferedReader(new FileReader(file));
            Log.i("aaaa", "准备读数据");
            //每次读取文件的缓存
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
                flag = 1;
                Log.i("aaaa", "数据读取中。。。");
            }
            Log.i("aaaa", "数据读取完成");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                    Log.i("aaaa", "关闭reader");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.i("aaaa", "flag:" + flag);

        if (flag == 0) {
            jsonObject = new JSONObject();
            try {
                Log.i("aaaa", "Json建立完成");
                for (String str : PARA) {
                    if (!(jsonObject.has(str))) {
                        jsonObject.put(str, "0");
                        Log.i("aaaa", str + "正在被初始化");
                    }
                }
                Log.i("aaaa", "Json初始化完成");
            } catch (JSONException ex) {
                // 键为null或使用json不支持的数字格式(NaN, infinities)
                throw new RuntimeException(ex);
            }
            return jsonObject.toString();
        }
        Log.i("aaaa", "返回数据:" + data.toString());
        return data.toString();
    }

    //给定路径与Json文件，存储到硬盘
    public void writeJson(Object json, String fileName) {
        BufferedWriter writer = null;
        File file = new File(sdCard + "//" + fileName + ".json");
        //如果文件不存在，则新建一个
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("文件写入成功！");
    }
}