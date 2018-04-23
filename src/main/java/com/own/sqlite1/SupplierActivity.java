package com.own.sqlite1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.own.sqlite1.adapter.SupplierAdapter;
import com.own.sqlite1.model.Supplier;
import com.own.sqlite1.sqlite.DBOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alo_m on 04/04/2018.
 */

public class SupplierActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtLastname;
    private EditText edtType;
    private Button btnSaveSupplier;
    private DBOperations operations;
    private Supplier supplier = new Supplier();
    private RecyclerView rvSuppliers;
    private List<Supplier> suppliers=new ArrayList<Supplier>();
    private SupplierAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        //iniciacion de la isntancia
        operations=DBOperations.getDBOperations(getApplicationContext());
        supplier.setIdSupplier("");


        initComponents();
    }
    private void initComponents(){
        rvSuppliers=(RecyclerView)findViewById(R.id.rv_supplier_list);
        rvSuppliers.setHasFixedSize(true);
        LinearLayoutManager layoutManeger=new LinearLayoutManager(this);
        rvSuppliers.setLayoutManager(layoutManeger);
        //
        getSuppliers();
        adapter=new SupplierAdapter(suppliers);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_delete_supplier:
                        operations.deleteSupplier(suppliers.get(rvSuppliers.getChildPosition((View)v.getParent().getParent())).getIdSupplier());
                        getSuppliers();
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.btn_edit_supplier:

                        Toast.makeText(getApplicationContext(),"Editar",Toast.LENGTH_SHORT).show();
                        Cursor c = operations.getSupplierById(suppliers.get(
                                rvSuppliers.getChildPosition(
                                        (View)v.getParent().getParent())).getIdSupplier());
                        if (c!=null){
                            if (c.moveToFirst()){
                                supplier = new Supplier(c.getString(1),c.getString(2),c.getString(3),c.getString(4));
                            }
                            edtName.setText(supplier.getName());
                            edtLastname.setText(supplier.getLastname());
                            edtType.setText(supplier.getType());
                        }else{
                            Toast.makeText(getApplicationContext(),"Registro no encontrado",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });
        rvSuppliers.setAdapter(adapter);

        edtName=(EditText)findViewById(R.id.et_name);
        edtLastname=(EditText)findViewById(R.id.et_lastname);
        edtType=(EditText)findViewById(R.id.et_type_supplier);

        btnSaveSupplier=(Button)findViewById(R.id.bt_save_supplier);

        btnSaveSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!supplier.getIdSupplier().equals("")){
                    supplier.setName(edtName.getText().toString());
                    supplier.setLastname(edtLastname.getText().toString());
                    supplier.setType(edtType.getText().toString());
                    operations.updateSupplier(supplier);

                }else {
                    supplier = new Supplier("", edtName.getText().toString(), edtLastname.getText().toString(), edtType.getText().toString());
                    operations.insertSupplier(supplier);
                }
                getSuppliers();
                clearData();
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void getSuppliers(){
        Cursor c =operations.getSuppliers();
        suppliers.clear();
        if(c!=null){
            while (c.moveToNext()){
                suppliers.add(new Supplier(c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
            }
        }

    }

    private void clearData(){
        edtName.setText("");
        edtLastname.setText("");
        edtType.setText("");
        supplier=null;
        supplier= new Supplier();
        supplier.setIdSupplier("");
    }
}
