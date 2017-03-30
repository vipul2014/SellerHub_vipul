package com.example.chintu.sellerhub.returns_module;

/**
 * Created by hp lap on 30-03-2017.
 */

public class Returns_Recycler_DataCollect {
    int artwork_id,return_id;

    public int getReturn_id() {
        return return_id;
    }

    public void setReturn_id(int return_id) {
        this.return_id = return_id;
    }

    String title,requested_on,selling_price,return_type,image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtwork_id() {
        return artwork_id;
    }

    public void setArtwork_id(int artwork_id) {
        this.artwork_id = artwork_id;
    }

    public String getRequested_on() {
        return requested_on;
    }

    public void setRequested_on(String requested_on) {
        this.requested_on = requested_on;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
