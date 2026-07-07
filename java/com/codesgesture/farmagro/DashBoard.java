package com.codesgesture.farmagro;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codesgesture.farmagro.Adapter.CategoryAdapter;
import com.codesgesture.farmagro.Models.CategoryModel;
import com.codesgesture.farmagro.Models.CustomerModel;
import com.codesgesture.farmagro.Services.CallJsonWithoutProgress;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.Constants;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    ShimmerFrameLayout shimmer_category;
    RecyclerView rvcat;
    LinearLayout cartbtn,account,category,home;
    public static final String PREFS_NAME = "settings";
    String GuestID;
    SharedPreferences prefs;
    CustomerModel customerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View vw=navigationView.getHeaderView(0);

        home=findViewById(R.id.home);
        category=findViewById(R.id.category);
        account=findViewById(R.id.account);
        cartbtn=findViewById(R.id.cartbtn);
        shimmer_category=findViewById(R.id.shimmer_category);
        rvcat=findViewById(R.id.rvcat);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,CartPage.class));
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,CategoryPage.class));
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManage.getCurrentUser(DashBoard.this) == null )
                {
                    startActivity(new Intent(DashBoard.this,LoginPage.class));
                }
                else {
                     startActivity(new Intent(DashBoard.this,PageAccount.class));
                }
            }
        });


        AddGuestID();

        shimmer_category.startShimmer();

        GridLayoutManager mLayoutManager = new GridLayoutManager(DashBoard.this, 3);
        rvcat.setLayoutManager(mLayoutManager);
        categoryAdapter = new CategoryAdapter(DashBoard.this, categoryModels,R.layout.item_category);
        rvcat.setAdapter(categoryAdapter);
        GetData();

    }

    private void AddGuestID() {

    }

    private void GetData() {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DashBoard.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(DashBoard.this, DashBoard.class));
        }  else if (id == R.id.nav_rate) {
            rateApp();
        }else if (id == R.id.nav_share) {
             UserUtil.ShareApp(this);
        }else if (id == R.id.nav_myac) {
            if (SessionManage.getCurrentUser(DashBoard.this) == null )
            {
                startActivity(new Intent(DashBoard.this,LoginPage.class));
            }
            else {
               // startActivity(new Intent(DashBoard.this,LoginPage.class));
            }
        }else if (id == R.id.nav_myorder) {
            if (SessionManage.getCurrentUser(DashBoard.this) == null )
            {
                startActivity(new Intent(DashBoard.this,LoginPage.class));
            }
            else {
               startActivity(new Intent(DashBoard.this,PageOrder.class));
            }
        }else if (id == R.id.nav_cart) {
             startActivity(new Intent(DashBoard.this,CartPage.class));
        }else if (id == R.id.nav_developer) {
             startActivity(new Intent(DashBoard.this,PageDeveloper.class));
        }else if (id == R.id.nav_contact) {
             startActivity(new Intent(DashBoard.this,PageContact.class));
        }else if (id == R.id.nav_about) {
             startActivity(new Intent(DashBoard.this,PageAbout.class));
        }else if (id == R.id.nav_term) {
              startActivity(new Intent(DashBoard.this,PageTerm.class));
        }else if (id == R.id.nav_privacy) {
             startActivity(new Intent(DashBoard.this,PagePrivacy.class));
        }else if (id == R.id.nav_logout) {
            if (SessionManage.getCurrentUser(DashBoard.this) == null )
            {
                startActivity(new Intent(DashBoard.this,LoginPage.class));
            }
            else {
                SessionManage.ClearSession(getApplicationContext());
                startActivity(new Intent(DashBoard.this,MainActivity.class));
                finish();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void rateApp() {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 26)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
    
}
