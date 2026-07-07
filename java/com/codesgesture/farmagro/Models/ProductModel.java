package com.codesgesture.farmagro.Models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String id;
    private String product_id;
    private String product_eng_name;
    private String product_hindi_name;
    private String product_description;
    private String category_id;
    private String category_name;
    private String product_unit;
    private String product_unit_value;
    private String product_sell_price;
    private String product_stock;
    private String product_photo;
    private String product_status;
    private String product_gst;
    private String product_gst_amount;
    private String product_cost_price;

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    private String shipping_charge;

    public int getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(int cart_qty) {
        this.cart_qty = cart_qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    private int cart_qty;
    private int qty;


    // Getter Methods 

    public String getId() {
        return id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_eng_name() {
        return product_eng_name;
    }

    public String getProduct_hindi_name() {
        return product_hindi_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public String getProduct_unit_value() {
        return product_unit_value;
    }

    public String getProduct_sell_price() {
        return product_sell_price;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public String getProduct_status() {
        return product_status;
    }

    public String getProduct_gst() {
        return product_gst;
    }

    public String getProduct_gst_amount() {
        return product_gst_amount;
    }

    public String getProduct_cost_price() {
        return product_cost_price;
    }

    // Setter Methods 

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_eng_name(String product_eng_name) {
        this.product_eng_name = product_eng_name;
    }

    public void setProduct_hindi_name(String product_hindi_name) {
        this.product_hindi_name = product_hindi_name;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public void setProduct_unit_value(String product_unit_value) {
        this.product_unit_value = product_unit_value;
    }

    public void setProduct_sell_price(String product_sell_price) {
        this.product_sell_price = product_sell_price;
    }

    public void setProduct_stock(String product_stock) {
        this.product_stock = product_stock;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public void setProduct_gst(String product_gst) {
        this.product_gst = product_gst;
    }

    public void setProduct_gst_amount(String product_gst_amount) {
        this.product_gst_amount = product_gst_amount;
    }

    public void setProduct_cost_price(String product_cost_price) {
        this.product_cost_price = product_cost_price;
    }
}
