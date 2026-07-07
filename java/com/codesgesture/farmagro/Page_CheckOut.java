package com.codesgesture.farmagro;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codesgesture.farmagro.Models.AddressModel;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Models.ProductModel;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Page_CheckOut extends AppCompatActivity {
    String carttotal,market_price_total;
    CustomerModel customerModel;
    TextView txmrp,txsmrp,txpamt,btorder;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ArrayList<ProductModel> productModels;
    ArrayList<AddressModel> addressModels;
    double discount=0,shipping=0,coupondis=0,totalpay=0;
    String GuestID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_details);
        customerModel=(CustomerModel) SessionManage.getCurrentUser(getApplicationContext());
        carttotal=getIntent().getStringExtra("total");
        market_price_total=getIntent().getStringExtra("market_price_total");
        productModels=(ArrayList<ProductModel>)getIntent().getSerializableExtra("productModel");
        addressModels=(ArrayList<AddressModel>)getIntent().getSerializableExtra("address");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Page_CheckOut.this);
        GuestID = prefs.getString("guest_id","empty");

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Order Checkout");

        radioGroup=findViewById(R.id.radioGroup);
        txmrp=findViewById(R.id.txmrp);
        txsmrp=findViewById(R.id.txsmrp);
        txpamt=findViewById(R.id.txpamt);
        btorder=findViewById(R.id.btorder);

        txmrp.setText(market_price_total);
        txsmrp.setText(productModels.get(0).getShipping_charge());

        shipping=Double.parseDouble(txsmrp.getText().toString());
        totalpay = Double.parseDouble(carttotal) + shipping;

        txpamt.setText(String.format("%.02f",totalpay));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton=findViewById(i);
                radioButton.getText().toString();
            }
        });

        btorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderNow();
            }
        });

    }

    private void OrderNow() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        String orderitms= new Gson().toJson(productModels);
        String address= new Gson().toJson(addressModels);
        CallJson jc = new CallJson(Page_CheckOut.this);
        param.add(new NetParam("payment_mode",radioButton.getText().toString()));
        float f = Float.parseFloat(carttotal);
        int ctotal = (int) f;
        param.add(new NetParam("total_order_amount",String.valueOf(ctotal)));
        param.add(new NetParam("items",orderitms));
        param.add(new NetParam("address",address));
        param.add(new NetParam("guest_id",GuestID));
        param.add(new NetParam("customer_id",customerModel.getCustomer_id()));
        jc.SendRequest("addorder", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                startActivity(new Intent(Page_CheckOut.this,PageSuccefull.class));
                UserUtil.ShowMsg("Order Placed !!",Page_CheckOut.this);
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }
}
