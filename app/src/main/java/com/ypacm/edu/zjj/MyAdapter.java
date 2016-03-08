package com.ypacm.edu.zjj;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by DB on 2016/3/4.
 */
public class MyAdapter extends BaseAdapter {


    int resquest_Code = 1;
    private List<ItemBean> mlist;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<ItemBean> list) {
        mlist = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            viewHolder.id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.companyName = (TextView) convertView.findViewById(R.id.tv_companyName);
            viewHolder.down = (TextView) convertView.findViewById(R.id.tv_down);
            viewHolder.score = (TextView) convertView.findViewById(R.id.tv_score);
            viewHolder.rank = (TextView) convertView.findViewById(R.id.tv_rank);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.btn_list = (Button) convertView.findViewById(R.id.btn_input);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemBean bean = mlist.get(position);

        DecimalFormat df=new DecimalFormat("#########.00");
        viewHolder.id.setText("编号：" +bean.item_id);
        viewHolder.companyName.setText("公司名称：" + bean.item_companyName);
        viewHolder.down.setText("下浮率：" + df.format(bean.item_down));
        viewHolder.score.setText("综合得分：" + df.format(bean.item_score));
        Log.i("aaaa", "" + bean.item_score);
        viewHolder.rank.setText("按综合分降序名次：" + bean.item_rank);
        viewHolder.price.setText("报价：" + df.format(bean.item_price));

        viewHolder.btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataInput.myThis, CustomDataInput.class);
                //DataInput.myThis.startActivity(intent);
                ItemBean bean = mlist.get(position);
                intent.putExtra("id", position);
                intent.putExtra("price", bean.item_price);
                intent.putExtra("technical", bean.item_technical);
                intent.putExtra("zixin", bean.item_zixin);
                intent.putExtra("seniority", bean.item_seniority);

                DataInput.myThis.startActivityForResult(intent, resquest_Code);
                ItemBean temp = mlist.get(position);
                temp.item_price = bean.item_price;
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView id;
        public TextView companyName;
        public TextView down;
        public TextView score;
        public TextView rank;
        public TextView price;
        public Button btn_list;
    }
}
