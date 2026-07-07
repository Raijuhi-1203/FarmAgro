package com.codesgesture.farmagro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codesgesture.farmagro.Adapter.CategoryAdapter;
import com.codesgesture.farmagro.Adapter.ProductAdapter;
import com.codesgesture.farmagro.Models.CategoryModel;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Models.ProductModel;
import com.codesgesture.farmagro.Services.CallJsonWithoutProgress;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.interfaces.Notify;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PageProduct extends AppCompatActivity {
    LinearLayout norecord;
    ProductAdapter categoryAdapter;
    ArrayList<ProductModel> categoryModels=new ArrayList<>();
    RecyclerView rvcat;
    CustomerModel customerModel;
    String catid,catnm;
    ShimmerFrameLayout shimmer_category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        catid=getIntent().getStringExtra("data");
        catnm=getIntent().getStringExtra("catnm");

        norecord=findViewById(R.id.norecord);
        shimmer_category=findViewById(R.id.shimmer_category);
        rvcat=findViewById(R.id.rvcat);

        shimmer_category.startShimmer();

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText(catnm);

        GridLayoutManager mLayoutManager = new GridLayoutManager(PageProduct.this, 1);
        rvcat.setLayoutManager(mLayoutManager);
        categoryAdapter = new ProductAdapter(PageProduct.this, categoryModels, R.layout.item_product, new Notify() {
            @Override
            public void onAdd(ProductModel data) {
                GetData();
            }

            @Override
            public void onRemove(ProductModel data) {
                GetData();
            }
        });
        rvcat.setAdapter(categoryAdapter);
        GetData();
    }

    private void GetData() {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageProduct.this);
        param.add(new NetParam("category_id",catid));
        param.add(new NetParam("customer_id",""));
        jc.SendRequest("get_catwiseproduct", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                shimmer_category.stopShimmer();
                shimmer_category.setVisibility(View.GONE);
                rvcat.setVisibility(View.VISIBLE);

                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    ProductModel product = new ProductModel();
                    product.setId(obj.getString("id"));
                    product.setProduct_id(obj.getString("product_id"));
                    product.setProduct_description(obj.getString("product_description"));
                    product.setProduct_unit(obj.getString("product_unit"));
                    product.setProduct_unit_value(obj.getString("product_unit_value"));
                    product.setProduct_stock(obj.getString("product_stock"));
                    product.setProduct_sell_price(obj.getString("product_sell_price"));

                    product.setProduct_eng_name(obj.getString("product_eng_name"));
                    product.setProduct_hindi_name(obj.getString("product_hindi_name"));
                    product.setCategory_id(obj.getString("category_id"));
                    product.setCategory_name(obj.getString("category_name"));
                    product.setProduct_photo(obj.getString("product_photo"));
                    product.setProduct_gst(obj.getString("product_gst"));
                    product.setProduct_gst_amount(obj.getString("product_gst_amount"));
                    product.setProduct_cost_price(obj.getString("product_cost_price"));
                    product.setShipping_charge(obj.getString("shipping_charge"));
                    product.setCart_qty(Integer.parseInt(obj.getString("cart_qty")));
                    product.setQty(Integer.parseInt(obj.getString("cart_qty")));
                    categoryModels.add(product);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }

}
