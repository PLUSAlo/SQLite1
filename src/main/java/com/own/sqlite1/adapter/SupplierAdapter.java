package com.own.sqlite1.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.own.sqlite1.R;
import com.own.sqlite1.model.Supplier;

import java.util.List;

/**
 * Created by alo_m on 04/04/2018.
 */

public class SupplierAdapter extends RecyclerView.Adapter <SupplierAdapter.SupplierViewHolder>
        implements View.OnClickListener {
    List<Supplier> suppliers;
    View.OnClickListener listener;
    //Constructor
    public SupplierAdapter(List<Supplier> suppliers){
        this.suppliers=suppliers;
    }
    //getter and setter de listener
    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SupplierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //se inflael cardview al reciclerview
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_cell_layout,parent,false);
        SupplierViewHolder holder=new SupplierViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SupplierViewHolder holder, int position) {
        System.out.println(suppliers.get(position));
        holder.tvSupplierName.setText(suppliers.get(position).getName());
        holder.tvSupplierLastname.setText(suppliers.get(position).getLastname());
        holder.tvSupplierType.setText(suppliers.get(position).getType());
        holder.setListener(this);
    }


    @Override
    public int getItemCount() {
        return suppliers.size();
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public static class SupplierViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cvSupplier;
        TextView tvSupplierName;
        TextView tvSupplierLastname;
        TextView tvSupplierType;
        ImageButton btEditSupplier;
        ImageButton btDeleteSupplier;
        View.OnClickListener listener;




        public SupplierViewHolder(View itemView) {
            super(itemView);
            cvSupplier =(CardView) itemView.findViewById(R.id.crv_supplier);
            tvSupplierName=(TextView)itemView.findViewById(R.id.txv_supplier);
            tvSupplierLastname=(TextView)itemView.findViewById(R.id.txv_lastname_supplier);
            tvSupplierType=(TextView)itemView.findViewById(R.id.txv_type);
            btEditSupplier=(ImageButton) itemView.findViewById(R.id.btn_edit_supplier);
            btDeleteSupplier=(ImageButton) itemView.findViewById(R.id.btn_delete_supplier);
            btEditSupplier.setOnClickListener(this);
            btDeleteSupplier.setOnClickListener(this);


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
