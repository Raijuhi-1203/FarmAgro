package com.codesgesture.farmagro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codesgesture.farmagro.CancelOrderForm;
import com.codesgesture.farmagro.Models.OrderProductModel;
import com.codesgesture.farmagro.R;
import com.codesgesture.farmagro.Utils.Constants;
import com.codesgesture.farmagro.Utils.SessionManage;

import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private ArrayList<OrderProductModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;
    String msg1;

    public OrderProductAdapter(Context context, ArrayList<OrderProductModel> arrayList, int layout,String msg) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        this.msg1=msg;
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getCustomer_id();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final OrderProductModel data = arrayList.get(i);

        holder.txprnm.setText(data.getProduct_eng_name()+" "+data.getProduct_hindi_name());
        holder.qty.setText("Qty : "+data.getProduct_qty());
        holder.mrp.setText("₹ "+data.getProduct_sell_price());

        String url = Constants.BASEURI2+data.getProduct_photo();
        Glide.with(context).load(url).into(holder.imgprd);

       if ( msg1.equals("cancel")){
            holder.cancel.setVisibility(View.GONE);
        }

       if (data.getOrder_status().equals("Cancelled")){
           holder.cancel.setVisibility(View.GONE);
       }

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CancelOrderForm.class);
                intent.putExtra("orderproduct",data);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txprnm,qty,mrp;
        ImageView imgprd;
        Button cancel;

        ViewHolder(View view) {
            super(view);
            txprnm = view.findViewById(R.id.txprnm);
            qty= view.findViewById(R.id.qty);
            mrp= view.findViewById(R.id.mrp);
            imgprd= view.findViewById(R.id.imgprd);
            cancel= view.findViewById(R.id.cancel);
        }
    }

    public void updateList(ArrayList<OrderProductModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}