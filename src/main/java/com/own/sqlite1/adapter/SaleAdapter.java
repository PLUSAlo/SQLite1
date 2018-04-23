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
import com.own.sqlite1.model.Supplier;
import com.own.sqlite1.model.MethodPayment;
import com.own.sqlite1.model.SaleHeader;
import com.own.sqlite1.sqlite.DBOperations;

import java.util.List;

/**
 * Created by alo_m on 04/04/2018.
 */

public class SaleAdapter  extends RecyclerView.Adapter <SaleAdapter.SaleViewHolder>
        implements View.OnClickListener{
    List<SaleHeader> sales;
    Supplier supplier;
    MethodPayment method;
    DBOperations operations;
    View.OnClickListener listener;
    //Constructor
    public SaleAdapter(List<SaleHeader> sales, DBOperations operations){
        this.sales = sales;
        this.operations = operations;

    }
    //getter and setter de listener
    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //se infla el cardview al reciclerview
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_cell_layout,parent,false);
        SaleViewHolder holder=new SaleViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SaleViewHolder holder, int position) {
        Cursor c = operations.getSupplierById(sales.get(position).getIdSupplier());
        if(c.moveToNext()){
            supplier = new Supplier(c.getString(1), c.getString(2), c.getString(3),
                    c.getString(4));
        }
        c=operations.getMethodPaymentById(sales.get(position).getIdMethodPayment());
        if(c.moveToNext()){
            method = new MethodPayment(c.getString(1), c.getString(2));
        }
        holder.txvSupplier.setText(supplier.getName()+" "+supplier.getLastname());
        holder.txvMethod.setText(method.getName());
        holder.txvDate.setText(sales.get(position).getDate());
        holder.txvTotal.setText(String.valueOf(sales.get(position).getTotal()));
        holder.setListener(this);
    }



    @Override
    public int getItemCount() {
        return sales.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public static class SaleViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        CardView crvSale;
        TextView txvSupplier;
        TextView txvMethod;
        TextView txvDate;
        TextView txvTotal;
        ImageButton btnEdit;
        ImageButton btnDelete;
        ImageButton btnDetail;
        View.OnClickListener listener;




        public SaleViewHolder(View itemView) {
            super(itemView);
            crvSale=(CardView) itemView.findViewById(R.id.crv_sale);
            txvSupplier =(TextView)itemView.findViewById(R.id.txv_supplier);
            txvMethod=(TextView)itemView.findViewById(R.id.txv_method_payment);
            txvDate=(TextView)itemView.findViewById(R.id.txv_date);
            btnEdit =(ImageButton) itemView.findViewById(R.id.btn_edit);
            btnDelete =(ImageButton) itemView.findViewById(R.id.btn_delete);
            btnDetail =(ImageButton) itemView.findViewById(R.id.btn_detail);
            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            btnDetail.setOnClickListener(this);
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
