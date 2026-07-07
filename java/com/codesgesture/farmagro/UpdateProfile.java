package com.codesgesture.farmagro;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Services.Utility;
import com.codesgesture.farmagro.Utils.SessionManage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateProfile extends AppCompatActivity {
    Button btn_submit,btn_save;
    EditText txnm,txmail,txmob,txcpass,txnpass;
    Spinner spnrgender;
    CustomerModel customerModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        customerModel=(CustomerModel) SessionManage.getCurrentUser(getApplicationContext());

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Update Profile");

        btn_save=findViewById(R.id.btn_save);
        btn_submit=findViewById(R.id.btn_submit);
        txnpass=findViewById(R.id.txnpass);
        txcpass=findViewById(R.id.txcpass);
        txmob=findViewById(R.id.txmob);
        txmail=findViewById(R.id.txmail);
        txnm=findViewById(R.id.txnm);
        spnrgender=findViewById(R.id.spnrgender);

        txnm.setText(customerModel.getCustomer_name());
        txmail.setText(customerModel.getCustomer_email());
        txmob.setText(customerModel.getCustomer_mobileno());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrgender.setAdapter(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txnm.getText().length()==0){
                    txnm.setError("Please enter name");
                }else  if (txmail.getText().length()==0){
                    txmail.setError("Please enter email id");
                }else if (txmob.getText().length()==0){
                    txmob.setError("Please enter mobile no");
                }else {
                    UpdateData();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txcpass.getText().length()==0){
                    txcpass.setError("Please enter current password");
                }else if(txnpass.getText().length()==0){
                    txnpass.setError("Please enter new password");
                }else {
                    UpdatePassword();
                }
            }
        });

    }

    private void UpdatePassword() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(UpdateProfile.this);
        param.add(new NetParam("password",txcpass.getText().toString()));
        param.add(new NetParam("newpass",txnpass.getText().toString()));
        param.add(new NetParam("custid",customerModel.getCustomer_id()));
        jc.SendRequest("update_password", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Password Updated !!",UpdateProfile.this);
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

    private void UpdateData() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(UpdateProfile.this);
        param.add(new NetParam("name",txnm.getText().toString()));
        param.add(new NetParam("mobile",txmob.getText().toString()));
        param.add(new NetParam("gender",spnrgender.getSelectedItem().toString()));
        param.add(new NetParam("mail",txmail.getText().toString()));
        jc.SendRequest("update_user", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Details Updated !!",UpdateProfile.this);
                CustomerModel sd = new CustomerModel();
                try {
                    JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                    if (obj.length() != 1) {
                        sd.setCustomer_date(obj.getString("customer_date"));
                        sd.setId(obj.getString("id"));
                        sd.setCustomer_email(obj.getString("customer_email"));
                        sd.setCustomer_gender(obj.getString("customer_gender"));
                        sd.setCustomer_id(obj.getString("customer_id"));
                        sd.setCustomer_mobileno(obj.getString("customer_mobileno"));
                        sd.setCustomer_name(obj.getString("customer_name"));
                        sd.setCustomer_password(obj.getString("customer_password"));
                        sd.setCustomer_profilephoto(obj.getString("customer_profilephoto"));
                        sd.setCustomer_status(obj.getString("customer_status"));
                        sd.setCustomer_temp_id(obj.getString("customer_temp_id"));
                        sd.setOtp(obj.getString("otp"));
                        sd.setEmail_verification_code(obj.getString("email_verification_code"));
                        sd.setEmail_verified(obj.getString("email_verified"));
                        sd.setMobileno_verified(obj.getString("mobileno_verified"));
                        sd.setCustomer_time(obj.getString("customer_time"));
                        SessionManage.SetCustomerSessions(getApplicationContext(), sd);
                        finish();
                    }
                } catch (JSONException e) {
                    Utility.ShowMEssage(UpdateProfile.this, "Invalid details !");
                    e.printStackTrace();
                }
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

}
