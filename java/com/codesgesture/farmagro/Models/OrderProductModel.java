package com.codesgesture.farmagro.Models;

import java.io.Serializable;

public class OrderProductModel implements Serializable {
    private String id;
    private String order_id_temp;
    private String order_id;
    private String sub_order_id_temp;
    private String sub_order_id;
    private String cart_no;
    private String order_date;
    private String order_time;
    private String customer_id;
    private String customer_name;
    private String customer_mobileno;
    private String customer_email;
    private String guest_id;
    private String billing_address_line1;
    private String billing_address_line2;
    private String billing_city_id = null;
    private String billing_city_name;
    private String billing_state_id = null;
    private String billing_state_name;
    private String billing_pincode;
    private String billing_landmark = null;
    private String product_id;
    private String product_eng_name;
    private String product_hindi_name;
    private String product_qty;
    private String product_unit;
    private String product_unit_value;
    private String product_sell_price;
    private String total_amount_of_product;
    private String total_order_amount;
    private String shipping_charge;
    private String payment_mode;
    private String payment_id = null;
    private String payment_request_id = null;
    private String payment_order_id = null;
    private String transaction_id = null;
    private String bank_transaction_id = null;
    private String transaction_status = null;
    private String transaction_gatway_name = null;
    private String transaction_payment_mode = null;
    private String refund_id = null;
    private String refund_status = null;
    private String order_shipping_status = null;
    private String order_delivery_date = null;
    private String order_delivery_time = null;
    private String order_cancel_date = null;
    private String order_cancel_time = null;
    private String order_return_reason = null;
    private String order_return_comment = null;
    private String order_status;
    private String product_photo;


    // Getter Methods 

    public String getId() {
        return id;
    }

    public String getOrder_id_temp() {
        return order_id_temp;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getSub_order_id_temp() {
        return sub_order_id_temp;
    }

    public String getSub_order_id() {
        return sub_order_id;
    }

    public String getCart_no() {
        return cart_no;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_mobileno() {
        return customer_mobileno;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public String getBilling_address_line1() {
        return billing_address_line1;
    }

    public String getBilling_address_line2() {
        return billing_address_line2;
    }

    public String getBilling_city_id() {
        return billing_city_id;
    }

    public String getBilling_city_name() {
        return billing_city_name;
    }

    public String getBilling_state_id() {
        return billing_state_id;
    }

    public String getBilling_state_name() {
        return billing_state_name;
    }

    public String getBilling_pincode() {
        return billing_pincode;
    }

    public String getBilling_landmark() {
        return billing_landmark;
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

    public String getProduct_qty() {
        return product_qty;
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

    public String getTotal_amount_of_product() {
        return total_amount_of_product;
    }

    public String getTotal_order_amount() {
        return total_order_amount;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getPayment_request_id() {
        return payment_request_id;
    }

    public String getPayment_order_id() {
        return payment_order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getBank_transaction_id() {
        return bank_transaction_id;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public String getTransaction_gatway_name() {
        return transaction_gatway_name;
    }

    public String getTransaction_payment_mode() {
        return transaction_payment_mode;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public String getOrder_shipping_status() {
        return order_shipping_status;
    }

    public String getOrder_delivery_date() {
        return order_delivery_date;
    }

    public String getOrder_delivery_time() {
        return order_delivery_time;
    }

    public String getOrder_cancel_date() {
        return order_cancel_date;
    }

    public String getOrder_cancel_time() {
        return order_cancel_time;
    }

    public String getOrder_return_reason() {
        return order_return_reason;
    }

    public String getOrder_return_comment() {
        return order_return_comment;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    // Setter Methods 

    public void setId(String id) {
        this.id = id;
    }

    public void setOrder_id_temp(String order_id_temp) {
        this.order_id_temp = order_id_temp;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setSub_order_id_temp(String sub_order_id_temp) {
        this.sub_order_id_temp = sub_order_id_temp;
    }

    public void setSub_order_id(String sub_order_id) {
        this.sub_order_id = sub_order_id;
    }

    public void setCart_no(String cart_no) {
        this.cart_no = cart_no;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setCustomer_mobileno(String customer_mobileno) {
        this.customer_mobileno = customer_mobileno;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public void setBilling_address_line1(String billing_address_line1) {
        this.billing_address_line1 = billing_address_line1;
    }

    public void setBilling_address_line2(String billing_address_line2) {
        this.billing_address_line2 = billing_address_line2;
    }

    public void setBilling_city_id(String billing_city_id) {
        this.billing_city_id = billing_city_id;
    }

    public void setBilling_city_name(String billing_city_name) {
        this.billing_city_name = billing_city_name;
    }

    public void setBilling_state_id(String billing_state_id) {
        this.billing_state_id = billing_state_id;
    }

    public void setBilling_state_name(String billing_state_name) {
        this.billing_state_name = billing_state_name;
    }

    public void setBilling_pincode(String billing_pincode) {
        this.billing_pincode = billing_pincode;
    }

    public void setBilling_landmark(String billing_landmark) {
        this.billing_landmark = billing_landmark;
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

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
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

    public void setTotal_amount_of_product(String total_amount_of_product) {
        this.total_amount_of_product = total_amount_of_product;
    }

    public void setTotal_order_amount(String total_order_amount) {
        this.total_order_amount = total_order_amount;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public void setPayment_request_id(String payment_request_id) {
        this.payment_request_id = payment_request_id;
    }

    public void setPayment_order_id(String payment_order_id) {
        this.payment_order_id = payment_order_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setBank_transaction_id(String bank_transaction_id) {
        this.bank_transaction_id = bank_transaction_id;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public void setTransaction_gatway_name(String transaction_gatway_name) {
        this.transaction_gatway_name = transaction_gatway_name;
    }

    public void setTransaction_payment_mode(String transaction_payment_mode) {
        this.transaction_payment_mode = transaction_payment_mode;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public void setOrder_shipping_status(String order_shipping_status) {
        this.order_shipping_status = order_shipping_status;
    }

    public void setOrder_delivery_date(String order_delivery_date) {
        this.order_delivery_date = order_delivery_date;
    }

    public void setOrder_delivery_time(String order_delivery_time) {
        this.order_delivery_time = order_delivery_time;
    }

    public void setOrder_cancel_date(String order_cancel_date) {
        this.order_cancel_date = order_cancel_date;
    }

    public void setOrder_cancel_time(String order_cancel_time) {
        this.order_cancel_time = order_cancel_time;
    }

    public void setOrder_return_reason(String order_return_reason) {
        this.order_return_reason = order_return_reason;
    }

    public void setOrder_return_comment(String order_return_comment) {
        this.order_return_comment = order_return_comment;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }
}