package com.ypacm.edu.zjj;

/**
 * Created by DB on 2016/3/4.
 */
public class ItemBean {
    public int item_id;
    public String item_companyName;
    public double item_down;
    public double item_score;
    public int item_rank;
    public double item_price;
    public double item_technical;
    public double item_zixin;
    public double item_seniority;
    public double item_business;

    public ItemBean(int item_id, String item_companyName, double item_down, double item_score, int item_rank, double item_price, double item_technical, double item_zixin, double item_seniority, double item_business) {
        this.item_id = item_id;
        this.item_companyName = item_companyName;
        this.item_down = item_down;
        this.item_score = item_score;
        this.item_rank = item_rank;
        this.item_price = item_price;
        this.item_technical = item_technical;
        this.item_zixin = item_zixin;
        this.item_seniority = item_seniority;
        this.item_business = item_business;
    }
}
