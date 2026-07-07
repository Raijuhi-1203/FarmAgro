package com.codesgesture.farmagro.interfaces;


import com.codesgesture.farmagro.Models.ProductModel;

public interface Notify {
    void onAdd(ProductModel data);
    void onRemove(ProductModel data);
}
