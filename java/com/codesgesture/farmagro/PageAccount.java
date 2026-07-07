package com.codesgesture.farmagro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codesgesture.farmagro.Models.CategoryModel;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Services.CallJsonWithoutProgress;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.SharedPref;
import com.codesgesture.farmagro.Utils.SessionManage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PageAccount extends AppCompatActivity {

    Button btn_update,btn_address,btn_logout;
    TextView txname,txmob;
    CustomerModel customerModel;
    TextView txtotalcartno,txtotalorderno;
    String GuestID;
    SharedPreferences prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        customerModel=(CustomerModel) SessionManage.getCurrentUser(getApplicationContext());

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        GuestID = prefs.getString("guest_id","empty");

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("My Account");

        btn_logout=findViewById(R.id.btn_logout);
        btn_address=findViewById(R.id.btn_address);
        btn_update=findViewById(R.id.btn_update);
        txname=findViewById(R.id.txname);
        txmob=findViewById(R.id.txmob);
        txtotalcartno=findViewById(R.id.txtotalcartno);
        txtotalorderno=findViewById(R.id.txtotalorderno);

        txname.setText(customerModel.getCustomer_name());
        txmob.setText(customerModel.getCustomer_mobileno());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManage.ClearSession(getApplicationContext());
                startActivity(new Intent(PageAccount.this,MainActivity.class));
                finish();
            }
        });

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageAccount.this,AddressBook.class));
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageAccount.this,UpdateProfile.class));
            }
        });

        GetTotal();
    }

    private void GetTotal() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageAccount.this);
        param.add(new NetParam("customer_id",customerModel.getCustomer_id()));
        param.add(new NetParam("guest_id",GuestID));
        jc.SendRequest("get_order_cart_no", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    txtotalcartno.setText(obj.getString("cartotal"));
                    txtotalorderno.setText(obj.getString("ordertotal"));
                }
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }

}
