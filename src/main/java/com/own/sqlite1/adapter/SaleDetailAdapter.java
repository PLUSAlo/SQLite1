package com.own.sqlite1.adapter;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.own.sqlite1.R;
import com.own.sqlite1.model.SaleDetail;
import com.own.sqlite1.model.Product;
import com.own.sqlite1.sqlite.DBOperations;

import java.util.List;

public class SaleDetailAdapter extends RecyclerView.Adapter<SaleDetailAdapter.SaleDetailViewHolder>
        implements View.OnClickListener {
    List<SaleDetail> details;
    Product product;
    DBOperations operations;
    View.OnClickListener listener;
    //Constructor
    public SaleDetailAdapter(List<SaleDetail> details,
                         DBOperations operations){
        this.details = details;
        this.operations = operations;

    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public SaleDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.detail_sale_layout, parent,false);
        SaleDetailViewHolder holder=new SaleDetailViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SaleDetailViewHolder holder, int position) {
        System.out.println("Id de producto: "
                +details.get(position).getIdProduct());

        Cursor c = operations.getProductById(
                details.get(position).getIdProduct());
        System.out.println(c);
        if(c.moveToNext()){
            product = new Product(c.getString(1), c.getString(2), c.getInt(3),
                    c.getFloat(4));
            System.out.println(product);
        }
        holder.txvProduct.setText(product.getName());
        holder.txvQuantity.setText(String.valueOf(details.get(position).getQuantity()));
        holder.setListener(this);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public static class SaleDetailViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        CardView crvDetail;
        TextView txvProduct;
        TextView txvQuantity;
        ImageButton btnEdit;
        ImageButton btnDelete;
        View.OnClickListener listener;
        public SaleDetailViewHolder(View itemView) {
            super(itemView);
            crvDetail = itemView.findViewById(R.id.crv_detail);
            txvProduct = itemView.findViewById(R.id.txv_product);
            txvQuantity = itemView.findViewById(R.id.txv_quantity);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onClick(v);
            }
        }
        public void setListener(View.OnClickListener listener){
            this.listener=listener;
        }
    }
}
