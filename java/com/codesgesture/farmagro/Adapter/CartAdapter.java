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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codesgesture.farmagro.Models.ProductModel;
import com.codesgesture.farmagro.PageProductDetails;
import com.codesgesture.farmagro.R;
import com.codesgesture.farmagro.Services.CallJson;
import com.codesgesture.farmagro.Services.CallJsonWithoutProgress;
import com.codesgesture.farmagro.Services.JsonCallbacks;
import com.codesgesture.farmagro.Services.NetParam;
import com.codesgesture.farmagro.Services.UserUtil;
import com.codesgesture.farmagro.Utils.Constants;
import com.codesgesture.farmagro.Utils.SessionManage;
import com.codesgesture.farmagro.interfaces.ExtraCallBack;
import com.codesgesture.farmagro.interfaces.Notify;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<ProductModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    Notify notify;
    ExtraCallBack ecb;
    int a=0;
    String GuestID;

    public CartAdapter(Context context, ArrayList<ProductModel> arrayList, int layout,Notify notify1) {
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

        holder.prdnm.setText(data.getProduct_eng_name()+" "+data.getProduct_hindi_name());
        holder.mrp.setText("₹ "+data.getProduct_cost_price());
        String url = Constants.BASEURI2+data.getProduct_photo();
        Glide.with(context).load(url).into(holder.prdimg);

        if(data.getCart_qty()!=0){
            holder.cartno.setText(String.valueOf(data.getCart_qty()));
        }
        holder.prdimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PageProductDetails.class);
                intent.putExtra("data",data);
                context.startActivity(intent);
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
                QtyChangeCart(totalamt,data.getCart_qty(),Userid,data.getProduct_id());
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cq =data.getQty();
                if (cq>0) {
                    cq--;
                    if(cq==0){
                        a=100;
                        RemoveCart(data.getProduct_id(),Userid,data.getId());
                    }else {
                        a=0;
                        data.setQty(cq);
                        data.setCart_qty(cq);
                        holder.cartno.setText(String.valueOf(data.getQty()));
                    }
                }

                if (a == 100) {

                }else if (a==0){
                    String price = data.getProduct_cost_price() ;
                    double totalamt = cq * Double.parseDouble(price) ;

                    notify.onRemove(null);
                    QtyChangeCart(totalamt,data.getQty(),Userid,data.getProduct_id());
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveCart(data.getProduct_id(),Userid,data.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prdnm,mrp,cartno;
        ImageView add,minus,remove;
        ImageView prdimg;

        ViewHolder(View view) {
            super(view);
            prdnm = (TextView) view.findViewById(R.id.prdnm);
            mrp = (TextView) view.findViewById(R.id.mrp);
            prdimg=(ImageView)view.findViewById(R.id.prdimg);
            add=view.findViewById(R.id.add);
            cartno=view.findViewById(R.id.cartno);
            minus=view.findViewById(R.id.minus);
            remove=view.findViewById(R.id.remove);
        }
    }

    public void updateList(ArrayList<ProductModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    private void QtyChangeCart(double totalamt, int cart_qty, String userid, String product_id) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress((Activity) context);
        param.add(new NetParam("product_id",product_id));
        param.add(new NetParam("cart_qty",String.valueOf(cart_qty)));
        param.add(new NetParam("customer_id",userid));
        param.add(new NetParam("cart_guest_id",GuestID));
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

    private void RemoveCart(String product_id, String userid, String product_final_sell_price) {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson((Activity) context);
        param.add(new NetParam("product_id",product_id));
        param.add(new NetParam("product_price_id",product_final_sell_price));
        param.add(new NetParam("customer_id",userid));
        param.add(new NetParam("cart_guest_id",GuestID));
        jc.SendRequest("removetocart", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Remove from Cart !!",context);
                if (ecb != null){
                    ecb.OnCompleted("removed");
                }
                notify.onAdd(null);
                BindCart();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

    private void BindCart() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress((Activity) context);
        param.add(new NetParam("customer_id",Userid));
        jc.SendRequest("get_cart_qty", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
                if (obj.length() != 1) {
                    Constants.CARTCOUNT = obj.getString("carttotal");
                }
            }

            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");

    }

}