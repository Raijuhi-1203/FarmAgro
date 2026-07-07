package com.codesgesture.farmagro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codesgesture.farmagro.Models.ProductModel;
import com.codesgesture.farmagro.PageProductDetails;
import com.codesgesture.farmagro.R;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.Constants;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.codesgesture.farmagro.interfaces.ExtraCallBack;
import com.codesgesture.farmagro.interfaces.Notify;

import org.json.JSONException;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<ProductModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    Notify notify;
    ExtraCallBack ecb;

    String GuestID;
    public static final String PREFS_NAME = "settings";
    public ProductAdapter(Context context, ArrayList<ProductModel> arrayList, int layout, Notify notify1) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        this.notify=notify1;
        if (SessionManage.getCurrentUser(context) == null )
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            GuestID = prefs.getString("guest_id","empty");
        }
        else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            GuestID = prefs.getString("guest_id","empty");
            this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getCustomer_id();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final ProductModel data = arrayList.get(i);

        holder.prdnm.setText(data.getProduct_eng_name()+" ("+data.getProduct_hindi_name()+")");
        holder.unit.setText(" ("+data.getProduct_unit_value()+" "+data.getProduct_unit()+")");
        holder.mrp.setText("₹ "+data.getProduct_cost_price());

        String url = Constants.BASEURI2 +data.getProduct_photo();
        Glide.with(context).load(url).into(holder.prdimg);

        holder.click_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PageProductDetails.class);
                intent.putExtra("data",data);
                context.startActivity(intent);
            }
        });

        if(data.getCart_qty()!=0){
            holder.btncart.setVisibility(View.GONE);
        }

        holder.btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCart(data.getProduct_id(),1,Userid,GuestID,data.getProduct_eng_name(),data.getProduct_hindi_name(),data.getProduct_cost_price(),data.getProduct_sell_price(),data.getProduct_unit(),data.getProduct_unit_value());
                holder.btncart.setVisibility(View.GONE);
                holder.cart_qty.setVisibility(View.VISIBLE);
                holder.cartno.setText("1");
                holder.cartno.setText(String.valueOf(data.getCart_qty()));
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cq =data.getCart_qty();
                cq++;
                data.setQty(cq);
                data.setCart_qty(cq);
                holder.cartno.setText(String.valueOf(data.getCart_qty()));
                String price = data.getProduct_cost_price() ;
                double totalamt = cq * Double.parseDouble(price) ;
                notify.onRemove(null);
                QtyChangeCart(data.getCart_qty(),Userid,data.getProduct_id(),totalamt);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cq =data.getQty();
                if (cq>1) {
                    cq--;
                    data.setQty(cq);
                    data.setCart_qty(cq);
                    holder.cartno.setText(String.valueOf(data.getQty()));
                }

                String price = data.getProduct_cost_price() ;
                double totalamt = cq * Double.parseDouble(price) ;
                notify.onRemove(null);
                QtyChangeCart(data.getQty(),Userid,data.getProduct_id(),totalamt);

            }
        });

    }

    private void AddCart(String product_id,int cart_qty, String userid, String guestID, String product_eng_name, String product_hindi_name, String product_cost_price, String product_sell_price, String product_unit, String product_unit_value) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("product_id",product_id));
        param.add(new NetParam("cart_qty",String.valueOf(cart_qty)));
        param.add(new NetParam("customer_id",userid));
        param.add(new NetParam("cart_guest_id",guestID));
        param.add(new NetParam("total_order_amount",product_cost_price));
        param.add(new NetParam("product_eng_name",product_eng_name));
        param.add(new NetParam("product_hindi_name",product_hindi_name));
        param.add(new NetParam("product_sell_price",product_sell_price));
        param.add(new NetParam("total_amount_of_product",product_cost_price));
        param.add(new NetParam("product_unit",product_unit));
        param.add(new NetParam("product_unit_value",product_unit_value));
        jc.SendRequest("addtocart", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Add to Cart !!",context);
                if (ecb != null){
                    ecb.OnCompleted("removed");
                }
                notify.onAdd(null);
            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }

    private void QtyChangeCart(int cart_qty, String userid, String product_id, double totalamt) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("product_id",product_id));
        param.add(new NetParam("cart_qty",String.valueOf(cart_qty)));
        param.add(new NetParam("customer_id",userid));
        param.add(new NetParam("cart_guest_id",""));
        param.add(new NetParam("total_order_amount",String.valueOf(String.format("%.02f",totalamt))));
        jc.SendRequest("addtocart_changeqty", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                // UserUtil.ShowMsg("change !!",context);
                if (ecb != null){
                    ecb.OnCompleted("removed");
                }
                notify.onAdd(null);
            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prdnm,mrp,cartno,unit;
        Button btncart;
        ImageView prdimg;
        LinearLayout click_layout,cart_qty;
        ImageView add,minus;

        ViewHolder(View view) {
            super(view);
            prdnm = (TextView) view.findViewById(R.id.prdnm);
            mrp = (TextView) view.findViewById(R.id.mrp);
            prdimg=(ImageView)view.findViewById(R.id.prdimg);
            click_layout= view.findViewById(R.id.click_layout);
            btncart= view.findViewById(R.id.btncart);
            add=(ImageView)view.findViewById(R.id.add);
            minus=(ImageView)view.findViewById(R.id.minus);
            cart_qty= view.findViewById(R.id.cart_qty);
            cartno = (TextView) view.findViewById(R.id.cartno);
            unit = (TextView) view.findViewById(R.id.unit);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(ArrayList<ProductModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}
