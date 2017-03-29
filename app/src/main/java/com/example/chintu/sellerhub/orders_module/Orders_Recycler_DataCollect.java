package com.example.chintu.sellerhub.orders_module;

/**
 * Created by hp lap on 28-03-2017.
 */

public class Orders_Recycler_DataCollect {
    int art_id,order_id,quantity;
    String title,order_date,Selling_price,Shipping_fee,payment_mode,image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getArt_id() {
        return art_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getSelling_price() {
        return Selling_price;
    }

    public void setSelling_price(String selling_price) {
        Selling_price = selling_price;
    }

    public String getShipping_fee() {
        return Shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        Shipping_fee = shipping_fee;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public void setArt_id(int art_id) {
        this.art_id = art_id;
    }
}
