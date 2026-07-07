package com.codesgesture.farmagro.Models;

public class BannerModel {
    private float id;
    private String banner_screen = null;
    private String banner_option = null;
    private String banner_path;
    private String banner_link;
    private String banner_status;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getBanner_screen() {
        return banner_screen;
    }

    public String getBanner_option() {
        return banner_option;
    }

    public String getBanner_path() {
        return banner_path;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public String getBanner_status() {
        return banner_status;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setBanner_screen(String banner_screen) {
        this.banner_screen = banner_screen;
    }

    public void setBanner_option(String banner_option) {
        this.banner_option = banner_option;
    }

    public void setBanner_path(String banner_path) {
        this.banner_path = banner_path;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public void setBanner_status(String banner_status) {
        this.banner_status = banner_status;
    }
}