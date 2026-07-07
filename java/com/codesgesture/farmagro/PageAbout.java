package com.codesgesture.farmagro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.SessionManage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PageAbout extends AppCompatActivity {
    TextView txpolicy;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        txpolicy=findViewById(R.id.txpolicy);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("About Us");

        BindPolicy();
    }

    private void BindPolicy() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageAbout.this);
        jc.SendRequest("get_about", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                if (obj.length() != 0) {
                    txpolicy.setText(Html.fromHtml(obj.getString("privacy_information")));
                }            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");

    }


}