package com.codesgesture.farmagro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Models.ProductImageeModel;
import com.codesgesture.farmagro.Models.ProductModel;
import com.codesgesture.farmagro.Models.UnitModel;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.CallJsonWithoutProgress;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.Constants;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.codesgesture.farmagro.interfaces.ExtraCallBack;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PageProductDetails extends AppCompatActivity {
    ProductModel productModel;
    ImageView add,minus,img;
    TextView txnm,mrp,fulldesp,bt_buy,btncart,cartqty,unit;
    CustomerModel customerModel;
    ExtraCallBack ecb;
    String GuestID;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        prefs =PreferenceManager.getDefaultSharedPreferences(this);
        GuestID = prefs.getString("guest_id","empty");
        if (GuestID.equals("empty")){
            GuestID=UserUtil.getRandomString(12);
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("guest_id", GuestID);
            editor.apply();
        }else {
            customerModel=(CustomerModel) SessionManage.getCurrentUser(getApplicationContext());
        }

        productModel=(ProductModel) getIntent().getSerializableExtra("data");

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText(productModel.getProduct_hindi_name());

        BindIds();

        String url = Constants.BASEURI2+productModel.getProduct_photo();
        Glide.with(PageProductDetails.this).load(url).into(img);

        if(productModel.getCart_qty()!=0){
            cartqty.setText(String.valueOf(productModel.getCart_qty()));
        }

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCart();
            }
        });

        unit.setText("("+productModel.getProduct_unit_value()+" "+productModel.getProduct_unit()+")");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cq =productModel.getCart_qty();
                cq++;
                productModel.setQty(cq);
                productModel.setCart_qty(cq);
                cartqty.setText(String.valueOf(cq));
                String price = productModel.getProduct_cost_price() ;
                double totalamt = cq * Double.parseDouble(price) ;
              //  QtyChangeCart(totalamt,productModel.getCart_qty(),customerModel.getCustomer_id(),productModel.getProduct_id(),productModel.getId());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cq =productModel.getQty();
                if (cq>1) {
                    cq--;
                    productModel.setQty(cq);
                    productModel.setCart_qty(cq);
                    cartqty.setText(String.valueOf(cq));
                }

                String price = productModel.getProduct_cost_price() ;
                double totalamt = cq * Double.parseDouble(price) ;

             //   QtyChangeCart(totalamt,productModel.getQty(),customerModel.getCustomer_id(),productModel.getProduct_id(),productModel.getId());
            }
        });

        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCart();
                Intent intent=new Intent(PageProductDetails.this, CartPage.class);
                startActivity(intent);
            }
        });

    }




    private void BindIds() {
        btncart=findViewById(R.id.btncart);bt_buy=findViewById(R.id.bt_buy);
        img=findViewById(R.id.img);add=findViewById(R.id.add);
        minus=findViewById(R.id.minus);txnm=findViewById(R.id.txnm);
        mrp=findViewById(R.id.mrp);unit=findViewById(R.id.unit);
        fulldesp=findViewById(R.id.fulldesp);

        cartqty=findViewById(R.id.cartqty);

        txnm.setText(productModel.getProduct_eng_name()+" "+productModel.getProduct_hindi_name());
        mrp.setText("₹ "+productModel.getProduct_cost_price());
        fulldesp.setText(Html.fromHtml(productModel.getProduct_description()));

    }



    private void AddCart() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageProductDetails.this);
        param.add(new NetParam("product_id",productModel.getProduct_id()));
        param.add(new NetParam("cart_qty",cartqty.getText().toString()));

        if (SessionManage.getCurrentUser(PageProductDetails.this) == null )
        {
            param.add(new NetParam("customer_id",""));
            param.add(new NetParam("cart_guest_id",GuestID));
        }
        else {
            param.add(new NetParam("customer_id",customerModel.getCustomer_id()));
            param.add(new NetParam("cart_guest_id",GuestID));
        }

        param.add(new NetParam("total_order_amount",productModel.getProduct_cost_price()));
        param.add(new NetParam("product_eng_name",productModel.getProduct_eng_name()));
        param.add(new NetParam("product_hindi_name",productModel.getProduct_hindi_name()));
        param.add(new NetParam("product_sell_price",productModel.getProduct_sell_price()));
        param.add(new NetParam("total_amount_of_product",productModel.getProduct_cost_price()));
        param.add(new NetParam("product_unit",productModel.getProduct_unit()));
        param.add(new NetParam("product_unit_value",productModel.getProduct_unit_value()));
        jc.SendRequest("addtocart", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Add to Cart !!",PageProductDetails.this);
                if (ecb != null){
                    ecb.OnCompleted("removed");
                }

            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }



}
