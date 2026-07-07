package com.codesgesture.farmagro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codesgesture.farmagro.Adapter.CartAdapter;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Models.ProductModel;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.codesgesture.farmagro.interfaces.Notify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartPage extends AppCompatActivity {
    RecyclerView recycler;
    LinearLayout norecord;
    CartAdapter cartAdapter;
    ArrayList<ProductModel> productModels=new ArrayList<>();
    CustomerModel customerModel;
    TextView cart_total,bt_checkout;
    double total;
    String totalfinal,price_total,GuestID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (SessionManage.getCurrentUser(CartPage.this) == null )
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CartPage.this);
            GuestID = prefs.getString("guest_id","empty");
        }
        else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CartPage.this);
            GuestID = prefs.getString("guest_id","empty");
            customerModel=(CustomerModel) SessionManage.getCurrentUser(getApplicationContext());
        }

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("My Cart");

        recycler=findViewById(R.id.recycler);
        norecord=findViewById(R.id.norecord);
        cart_total=findViewById(R.id.cart_total);
        bt_checkout=findViewById(R.id.bt_checkout);

        GridLayoutManager mLayoutManager2 = new GridLayoutManager(CartPage.this, 1);
        recycler.setLayoutManager(mLayoutManager2);
        cartAdapter = new CartAdapter(CartPage.this, productModels, R.layout.item_cart, new Notify() {
            @Override
            public void onAdd(ProductModel data) {
                GetProductData();
            }

            @Override
            public void onRemove(ProductModel data) {
                TotalAmount();
            }
        });

        recycler.setAdapter(cartAdapter);
        recycler.setItemViewCacheSize(productModels.size());
        GetProductData();

        bt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total==0){
                    UserUtil.ShowMsg("Sorry ! Your cart is Empty.", CartPage.this);
                }else {
                    cHECKoUT();
                }
            }
        });

    }

    private void GetProductData() {
        productModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(CartPage.this);

        if (SessionManage.getCurrentUser(CartPage.this) == null )
        {
            param.add(new NetParam("customer_id",""));
            param.add(new NetParam("cart_guest_id",GuestID));
        }
        else {
            param.add(new NetParam("customer_id",customerModel.getCustomer_id()));
            param.add(new NetParam("cart_guest_id",GuestID));
        }
        jc.SendRequest("get_cartproduct", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
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
                    productModels.add(product);
                }
                TotalAmount();
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }
    private void cHECKoUT() {
        if (SessionManage.getCurrentUser(CartPage.this) == null )
        {
            startActivity(new Intent(CartPage.this,LoginPage.class));
        }
        else {
            totalfinal=String.valueOf(total);
            Intent intent=new Intent(CartPage.this, PageAddress.class);
            intent.putExtra("total",totalfinal);
            intent.putExtra("market_price_total",price_total);
            intent.putExtra("productModel", productModels);
            startActivity(intent);
        }
    }
    private void checkcart() {
        if(productModels.size()<=0)
            norecord.setVisibility(View.VISIBLE);
        else
            norecord.setVisibility(View.GONE);
    }
    private void TotalAmount() {
        Double totalamount = 0.0;
        Double mtotalamount = 0.0;
        for (ProductModel cart : productModels) {
            totalamount += (Double.parseDouble(cart.getProduct_cost_price()) * cart.getCart_qty());
            mtotalamount += (Double.parseDouble(cart.getProduct_cost_price()) * cart.getCart_qty());
        }
        String price =String.valueOf(String.format("%.02f",totalamount));
        String mprice =String.valueOf(String.format("%.02f",mtotalamount));
        cart_total.setText("₹ "+price);
        total=totalamount;
        price_total=mprice;
        checkcart();
    }

}
