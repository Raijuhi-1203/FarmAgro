package com.codesgesture.farmagro.Models;

import java.io.Serializable;

public class TopSliderModel implements Serializable {
    private float id;
    private String slider_title = null;
    private String slider_photo;
    private String slider_status = null;
    private String slider_link;
    private String slider_mode;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getSlider_title() {
        return slider_title;
    }

    public String getSlider_photo() {
        return slider_photo;
    }

    public String getSlider_status() {
        return slider_status;
    }

    public String getSlider_link() {
        return slider_link;
    }

    public String getSlider_mode() {
        return slider_mode;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setSlider_title(String slider_title) {
        this.slider_title = slider_title;
    }

    public void setSlider_photo(String slider_photo) {
        this.slider_photo = slider_photo;
    }

    public void setSlider_status(String slider_status) {
        this.slider_status = slider_status;
    }

    public void setSlider_link(String slider_link) {
        this.slider_link = slider_link;
    }

    public void setSlider_mode(String slider_mode) {
        this.slider_mode = slider_mode;
    }
}
