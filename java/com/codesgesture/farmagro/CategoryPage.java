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
import com.codesgesture.farmagro.Models.CategoryModel;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Services.CallJsonWithoutProgress;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryPage extends AppCompatActivity {
    LinearLayout norecord;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    ShimmerFrameLayout shimmer_category;
    RecyclerView rvcat;
    CustomerModel customerModel;

    LinearLayout cartbtn,account,category,home;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        norecord=findViewById(R.id.norecord);
        home=findViewById(R.id.home);
        category=findViewById(R.id.category);
        account=findViewById(R.id.account);
        cartbtn=findViewById(R.id.cartbtn);

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Shop By Categories");


        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(CategoryPage.this,CartPage.class));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManage.getCurrentUser(CategoryPage.this) == null )
                {
                    startActivity(new Intent(CategoryPage.this,LoginPage.class));
                }
                else {
                     startActivity(new Intent(CategoryPage.this,PageAccount.class));
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(CategoryPage.this,DashBoard.class));
            }
        });


        shimmer_category=findViewById(R.id.shimmer_category);
        rvcat=findViewById(R.id.rvcat);

        shimmer_category.startShimmer();

        GridLayoutManager mLayoutManager = new GridLayoutManager(CategoryPage.this, 3);
        rvcat.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategoryAdapter(CategoryPage.this, categoryModels,R.layout.item_category);
        rvcat.setAdapter(categoryAdapter);
        GetData();
    }

    private void GetData() {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(CategoryPage.this);
        jc.SendRequest("get_Category", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {

                shimmer_category.stopShimmer();
                shimmer_category.setVisibility(View.GONE);
                rvcat.setVisibility(View.VISIBLE);

                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CategoryModel product = new CategoryModel();
                    product.setCategory_name(obj.getString("category_name"));
                    product.setCategory_id(obj.getString("category_id"));
                    product.setCategory_photo(obj.getString("category_photo"));
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
