package com.own.sqlite1.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.own.sqlite1.model.Customer;
import com.own.sqlite1.model.MethodPayment;
import com.own.sqlite1.model.Movie;
import com.own.sqlite1.model.OrderDetail;
import com.own.sqlite1.model.OrderHeader;
import com.own.sqlite1.model.Product;
import com.own.sqlite1.model.SaleDetail;
import com.own.sqlite1.model.SaleHeader;
import com.own.sqlite1.model.Supplier;


/**
 * Created by Marili Arevalo on 23/02/2017.
 */

public final class DBOperations {
    private static DBHelper db;
    private static DBOperations operations;

    private static final String JOIN_ORDER_CUSTOMER_METHOD =
            "order_header " +
            "INNER JOIN customer " +
            "ON order_header.id_customer = customer.id " +
            "INNER JOIN method_payment " +
            "ON order_header.id_method_payment = method_payment.id";
    private final String[] orderProj = new String[]{
            DBHelper.Tables.ORDER_HEADER + "." +
                    Contract.OrderHeaders.ID,
                    Contract.OrderHeaders.ID_CUSTOMER,
                    Contract.OrderHeaders.ID_METHOD_PAYMENT,
                    Contract.OrderHeaders.DATE,
                    Contract.Customers.FIRSTNAME,
                    Contract.Customers.LASTNAME,
                    Contract.MethodsPayment.NAME
            };

    private static final String JOIN_SALE_SUPPLIER_METHOD =
            "sale_header " +
                    "INNER JOIN supplier " +
                    "ON sale_header.id_supplier = supplier.id " +
                    "INNER JOIN method_payment " +
                    "ON sale_header.id_method_payment = method_payment.id";
    private final String[] saleProj = new String[]{
            DBHelper.Tables.SALE_HEADER + "." +
                    Contract.SaleHeaders.ID,
            Contract.SaleHeaders.ID_SUPPLIER,
            Contract.SaleHeaders.ID_METHOD_PAYMENT,
            Contract.SaleHeaders.DATE,
            Contract.SaleHeaders.TOTAL,
            Contract.Suppliers.NAME,
            Contract.Suppliers.LASTNAME,
            Contract.MethodsPayment.NAME
    };


    private DBOperations(){

    }

    public static DBOperations getDBOperations(
            Context context){
        if(operations==null) {
            operations = new DBOperations();
        }
        if(db==null){
            db = new DBHelper(context);
        }
        return operations;
    }
    //Operations of  Orders
    public Cursor getOrders(){
        SQLiteDatabase database = db.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_ORDER_CUSTOMER_METHOD);

        return  builder.query(database, orderProj,
                null, null, null, null, null);
    }

    public Cursor getOrderById(String id){
        SQLiteDatabase database = db.getWritableDatabase();
        String selection = String.format("%s=?",
                DBHelper.Tables.ORDER_HEADER+"."+Contract.OrderHeaders.ID);
        String[] selectionArgs = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_ORDER_CUSTOMER_METHOD);
        String[] projection = {
                DBHelper.Tables.ORDER_HEADER+"."
                        + Contract.OrderHeaders.ID,
                Contract.OrderHeaders.ID_CUSTOMER,
                Contract.OrderHeaders.ID_METHOD_PAYMENT,
                Contract.OrderHeaders.DATE,
                Contract.Customers.FIRSTNAME,
                Contract.Customers.LASTNAME,
                Contract.MethodsPayment.NAME
        };
        return builder.query(database, projection, selection,
                selectionArgs, null, null, null);
    }

    public String insertOrderHeader(OrderHeader orderHeader){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idOrderHeader =
                Contract.OrderHeaders.generateIdOrderHeader();
        values.put(Contract.OrderHeaders.ID, idOrderHeader);
        values.put(Contract.OrderHeaders.ID_CUSTOMER,
                orderHeader.getIdCustomer());
        values.put(Contract.OrderHeaders.ID_METHOD_PAYMENT,
                orderHeader.getIdMethodPayment());
        values.put(Contract.OrderHeaders.DATE,
                orderHeader.getDate());

        database.insertOrThrow(DBHelper.Tables.ORDER_HEADER,
                null, values);
        return idOrderHeader;
    }

    public boolean updateOrderHeader(OrderHeader orderHeader){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.OrderHeaders.DATE,
                orderHeader.getDate());
        values.put(Contract.OrderHeaders.ID_CUSTOMER,
                orderHeader.getIdCustomer());
        values.put(Contract.OrderHeaders.ID_METHOD_PAYMENT,
                orderHeader.getIdMethodPayment());

        String whereClause = String.format("%s=?", Contract.OrderHeaders.ID);
        String[] whereArgs = {orderHeader.getIdOrderHeader()};

        int result = database.update(DBHelper.Tables.ORDER_HEADER, values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteOrderHeader(String idOrderHeader){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                Contract.OrderHeaders.ID + "=?";
        String[] whereArgs = {idOrderHeader};

        int result =  database.delete(
                DBHelper.Tables.ORDER_HEADER, whereClause, whereArgs);
        return result > 0;
    }
    //Operations of  OrderDetails
    public Cursor getOrderDetails(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.ORDER_DETAIL);
        return  database.rawQuery(sql, null);
    }

    public Cursor getOrderDetailById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                " WHERE %s=?", DBHelper.Tables.ORDER_DETAIL,
                Contract.OrderHeaders.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertOrderDetail(OrderDetail orderDetail){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.OrderDetails.ID,
                orderDetail.getIdOrderHeader());
        values.put(Contract.OrderDetails.SEQUENCE,
                orderDetail.getSequence());
        values.put(Contract.OrderDetails.ID_PRODUCT,
                orderDetail.getIdProduct());
        values.put(Contract.OrderDetails.QUANTITY,
                orderDetail.getQuantity());
        values.put(Contract.OrderDetails.PRICE,
                orderDetail.getPrice());

        database.insertOrThrow(DBHelper.Tables.ORDER_DETAIL,
                null, values);
        return String.format("%s#%d",
                orderDetail.getIdOrderHeader(),
                orderDetail.getSequence());
    }

    public boolean updateOrderDetail(OrderDetail orderDetail){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.OrderDetails.SEQUENCE,
                orderDetail.getSequence());
        values.put(Contract.OrderDetails.QUANTITY,
                orderDetail.getQuantity());
        values.put(Contract.OrderDetails.PRICE,
                orderDetail.getPrice());

        String whereClause = String.format("%s=? AND %s=?",
                Contract.OrderDetails.ID,
                Contract.OrderDetails.SEQUENCE);
        String[] whereArgs = {orderDetail.getIdOrderHeader(),
        String.valueOf(orderDetail.getSequence())};

        int result = database.update(DBHelper.Tables.ORDER_DETAIL,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteOrderDetail(String idOrderDetail, String sequence){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                Contract.OrderDetails.ID + "=? AND "+
                Contract.OrderDetails.SEQUENCE + "=?";
        String[] whereArgs = {idOrderDetail, sequence};

        int result =  database.delete(
                DBHelper.Tables.ORDER_DETAIL, whereClause, whereArgs);
        return result > 0;
    }

    public boolean deleteOrderDetail(String idOrderDetail){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                Contract.OrderDetails.ID + "=?";
        String[] whereArgs = {idOrderDetail};

        int result =  database.delete(
                DBHelper.Tables.ORDER_DETAIL, whereClause, whereArgs);
        return result > 0;
    }




    //Operations of  Sales
    public Cursor getSales(){
        SQLiteDatabase database = db.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_SALE_SUPPLIER_METHOD);

        return  builder.query(database, saleProj,
                null, null, null, null, null);
    }

    public Cursor getSaleById(String id){
        SQLiteDatabase database = db.getWritableDatabase();
        String selection = String.format("%s=?",
                DBHelper.Tables.SALE_HEADER+"."+Contract.SaleHeaders.ID);
        String[] selectionArgs = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(JOIN_SALE_SUPPLIER_METHOD);
        String[] projection = {
                DBHelper.Tables.SALE_HEADER+"."
                        + Contract.SaleHeaders.ID,
                Contract.SaleHeaders.ID_SUPPLIER,
                Contract.SaleHeaders.ID_METHOD_PAYMENT,
                Contract.SaleHeaders.DATE,
                Contract.SaleHeaders.TOTAL,
                Contract.Suppliers.NAME,
                Contract.Suppliers.LASTNAME,
                Contract.MethodsPayment.NAME
        };
        return builder.query(database, projection, selection,
                selectionArgs, null, null, null);
    }

    public String insertSaleHeader(SaleHeader saleHeader){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idSaleHeader =
                Contract.SaleHeaders.generateIdSaleHeader();
        values.put(Contract.SaleHeaders.ID, idSaleHeader);
        values.put(Contract.SaleHeaders.ID_SUPPLIER,
                saleHeader.getIdSupplier());
        values.put(Contract.SaleHeaders.ID_METHOD_PAYMENT,
                saleHeader.getIdMethodPayment());
        values.put(Contract.SaleHeaders.DATE,
                saleHeader.getDate());
        values.put(Contract.SaleHeaders.TOTAL,
                saleHeader.getTotal());
        database.insertOrThrow(DBHelper.Tables.SALE_HEADER,
                null, values);
        return idSaleHeader;
    }

    public boolean updateSaleHeader(SaleHeader saleHeader){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.SaleHeaders.DATE,
                saleHeader.getDate());
        values.put(Contract.SaleHeaders.TOTAL,
                saleHeader.getTotal());
        values.put(Contract.SaleHeaders.ID_SUPPLIER,
                saleHeader.getIdSupplier());
        values.put(Contract.SaleHeaders.ID_METHOD_PAYMENT,
                saleHeader.getIdMethodPayment());

        String whereClause = String.format("%s=?", Contract.SaleHeaders.ID);
        String[] whereArgs = {saleHeader.getIdSaleHeader()};

        int result = database.update(DBHelper.Tables.SALE_HEADER, values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteSaleHeader(String idSaleHeader){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                Contract.SaleHeaders.ID + "=?";
        String[] whereArgs = {idSaleHeader};

        int result =  database.delete(
                DBHelper.Tables.SALE_HEADER, whereClause, whereArgs);
        return result > 0;
    }
    //Operations of  OrderDetails
    public Cursor getSaleDetails(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.SALE_DETAIL);
        return  database.rawQuery(sql, null);
    }

    public Cursor getSaleDetailById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                        " WHERE %s=?", DBHelper.Tables.SALE_DETAIL,
                Contract.SaleHeaders.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertSaleDetail(SaleDetail saleDetail){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.SaleDetails.ID,
                saleDetail.getIdSaleHeader());
        values.put(Contract.SaleDetails.SEQUENCE,
                saleDetail.getSequence());
        values.put(Contract.SaleDetails.ID_PRODUCT,
                saleDetail.getIdProduct());
        values.put(Contract.SaleDetails.QUANTITY,
                saleDetail.getQuantity());

        database.insertOrThrow(DBHelper.Tables.SALE_DETAIL,
                null, values);
        return String.format("%s#%d",
                saleDetail.getIdSaleHeader(),
                saleDetail.getSequence());
    }

    public boolean updateSaleDetail(SaleDetail saleDetail){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.SaleDetails.SEQUENCE,
                saleDetail.getSequence());
        values.put(Contract.SaleDetails.QUANTITY,
                saleDetail.getQuantity());

        String whereClause = String.format("%s=? AND %s=?",
                Contract.SaleDetails.ID,
                Contract.SaleDetails.SEQUENCE);
        String[] whereArgs = {saleDetail.getIdSaleHeader(),
                String.valueOf(saleDetail.getSequence())};

        int result = database.update(DBHelper.Tables.SALE_DETAIL,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteSaleDetail(String idSaleDetail, String sequence){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                Contract.SaleDetails.ID + "=? AND "+
                        Contract.SaleDetails.SEQUENCE + "=?";
        String[] whereArgs = {idSaleDetail, sequence};

        int result =  database.delete(
                DBHelper.Tables.SALE_DETAIL, whereClause, whereArgs);
        return result > 0;
    }

    public boolean deleteSaleDetail(String idSaleDetail){
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause =
                Contract.SaleDetails.ID + "=?";
        String[] whereArgs = {idSaleDetail};

        int result =  database.delete(
                DBHelper.Tables.SALE_DETAIL, whereClause, whereArgs);
        return result > 0;
    }




    //Operations of  Movie
    public Cursor getMovies(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.MOVIE);
        return  database.rawQuery(sql, null);
    }

    public Cursor getMovieById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                        " WHERE %s=?",
                DBHelper.Tables.MOVIE,
                Contract.Products.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertMovie(Movie movie){
        SQLiteDatabase database = db.getWritableDatabase();
        String idMovie = Contract.Movies.generateIdMovie();
        ContentValues values = new ContentValues();
        values.put(Contract.Movies.ID, idMovie);
        values.put(Contract.Movies.NAME, movie.getName());
        values.put(Contract.Movies.DIRECTOR, movie.getDirector());
        values.put(Contract.Movies.CLASIFICATION, movie.getClasification());
        values.put(Contract.Movies.YEAR, movie.getYear());
        values.put(Contract.Movies.DURATION, movie.getDuration());
        database.insertOrThrow(DBHelper.Tables.MOVIE, null, values);
        return idMovie;
    }

    public boolean updateMovie(Movie movie){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.Movies.NAME, movie.getName());
        values.put(Contract.Movies.DIRECTOR, movie.getDirector());
        values.put(Contract.Movies.CLASIFICATION, movie.getClasification());
        values.put(Contract.Movies.YEAR, movie.getYear());
        values.put(Contract.Movies.DURATION, movie.getDuration());
        String whereClause = String.format("%s=?", Contract.Movies.ID);
        String[] whereArgs = {movie.getIdMovie()};
        int result = database.update(DBHelper.Tables.MOVIE,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteMovie(String idMovie) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = Contract.Movies.ID + "=?";
        String[] whereArgs = {idMovie};
        int result = database.delete(DBHelper.Tables.MOVIE,
                whereClause, whereArgs);
        return result > 0;
    }





    //Operations of  Product
    public Cursor getProducts(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.PRODUCT);
        return  database.rawQuery(sql, null);
    }

    public Cursor getProductById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                        " WHERE %s=?",
                DBHelper.Tables.PRODUCT,
                Contract.Products.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertProduct(Product product){
        SQLiteDatabase database = db.getWritableDatabase();
        String idProduct = Contract.Products.generateIdProduct();
        ContentValues values = new ContentValues();
        values.put(Contract.Products.ID, idProduct);
        values.put(Contract.Products.NAME, product.getName());
        values.put(Contract.Products.PRICE, product.getPrice());
        values.put(Contract.Products.STOCK, product.getStock());
        database.insertOrThrow(DBHelper.Tables.PRODUCT, null, values);
        return idProduct;
    }

    public boolean updateProduct(Product product){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.Products.NAME, product.getName());
        values.put(Contract.Products.PRICE, product.getPrice());
        values.put(Contract.Products.STOCK, product.getStock());
        String whereClause = String.format("%s=?", Contract.Products.ID);
        String[] whereArgs = {product.getIdProduct()};
        int result = database.update(DBHelper.Tables.PRODUCT,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteProduct(String idProduct) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = Contract.Products.ID + "=?";
        String[] whereArgs = {idProduct};
        int result = database.delete(DBHelper.Tables.PRODUCT,
                whereClause, whereArgs);
        return result > 0;
    }



    // Operations Customers
    public Cursor getCustomers() {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.CUSTOMER);
        return database.rawQuery(sql, null);
    }
    public Cursor getCustomerById(String idCustomer) {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                DBHelper.Tables.CUSTOMER,
                Contract.Customers.ID);
        String[] whereArgs ={idCustomer};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertCustomer(Customer customer) {
        SQLiteDatabase database = db.getWritableDatabase();
        String idCustomer = Contract.Customers.generateIdCustomer();
        ContentValues values = new ContentValues();
        values.put(Contract.Customers.ID, idCustomer);
        values.put(Contract.Customers.FIRSTNAME, customer.getFirstname());
        values.put(Contract.Customers.LASTNAME, customer.getLastname());
        values.put(Contract.Customers.PHONE, customer.getPhone());

        return database.insertOrThrow(DBHelper.Tables.CUSTOMER,
                null, values) > 0 ? idCustomer : null;
    }

    public boolean updateCustomer(Customer customer) {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.Customers.FIRSTNAME,
                customer.getFirstname());
        values.put(Contract.Customers.LASTNAME,
                customer.getLastname());
        values.put(Contract.Customers.PHONE,
                customer.getPhone());
        String whereClause = String.format("%s=?",
                Contract.Customers.ID);
        final String[] whereArgs = {customer.getIdCustomer()};
        int result = database.update(DBHelper.Tables.CUSTOMER,
                values, whereClause, whereArgs);
        return result > 0;
    }
    public boolean deleteCustomer(String idCustomer) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = String.format("%s=?",
                Contract.Customers.ID);
        final String[] whereArgs = {idCustomer};
        int result = database.delete(DBHelper.Tables.CUSTOMER, whereClause, whereArgs);
        return result > 0;
    }






    // Operation Method of payment
    public Cursor getMethodsPayment() {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.METHOD_PAYMENT);
        return database.rawQuery(sql, null);
    }

    public Cursor getMethodPaymentById(String idMethodPayment) {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                DBHelper.Tables.METHOD_PAYMENT,
                Contract.MethodsPayment.ID);
        String[] whereArgs = {idMethodPayment};
        return database.rawQuery(sql, whereArgs);
    }
    public String insertMethodPayment(MethodPayment methodPayment) {
        SQLiteDatabase database = db.getWritableDatabase();

        String idMethodPayment = Contract.MethodsPayment.generateIdMethodPayment();

        ContentValues values = new ContentValues();
        values.put(Contract.MethodsPayment.ID, idMethodPayment);
        values.put(Contract.MethodsPayment.NAME, methodPayment.getName());

        return database.insertOrThrow(
                DBHelper.Tables.METHOD_PAYMENT, null,
                values) > 0 ? idMethodPayment : null;
    }

    public boolean updateMethodPayment(MethodPayment methodPayment) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.MethodsPayment.NAME,
                methodPayment.getName());
        String whereClause = String.format("%s=?",
                Contract.MethodsPayment.ID);
        String[] whereArgs = {methodPayment.getIdMethodPayment()};
        int result = database.update(
                DBHelper.Tables.METHOD_PAYMENT, values,
                whereClause, whereArgs);
        return result > 0;
    }

    public boolean deleteMethodPayment(String idMethodPayment) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = String.format("%s=?", Contract.MethodsPayment.ID);
        String[] whereArgs = {idMethodPayment};
        int result = database.delete(DBHelper.Tables.METHOD_PAYMENT, whereClause, whereArgs);
        return result > 0;
    }


    public SQLiteDatabase getDb() {
        return db.getWritableDatabase();
    }


    //Operations of  Supplier
    public Cursor getSuppliers(){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",
                DBHelper.Tables.SUPPLIER);
        return  database.rawQuery(sql, null);
    }

    public Cursor getSupplierById(String id){
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s" +
                        " WHERE %s=?",
                DBHelper.Tables.SUPPLIER,
                Contract.Suppliers.ID);
        String[] whereArgs = {id};
        return database.rawQuery(sql, whereArgs);
    }

    public String insertSupplier(Supplier supplier){
        SQLiteDatabase database = db.getWritableDatabase();
        String idSupplier = Contract.Suppliers.generateIdSupplier();
        ContentValues values = new ContentValues();
        values.put(Contract.Suppliers.ID, idSupplier);
        values.put(Contract.Suppliers.NAME, supplier.getName());
        values.put(Contract.Suppliers.LASTNAME, supplier.getLastname());
        values.put(Contract.Suppliers.TYPE, supplier.getType());
        database.insertOrThrow(DBHelper.Tables.SUPPLIER, null, values);
        return idSupplier;
    }

    public boolean updateSupplier(Supplier supplier){
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.Suppliers.NAME,supplier.getName());
        values.put(Contract.Suppliers.LASTNAME, supplier.getLastname());
        values.put(Contract.Suppliers.TYPE, supplier.getType());
        String whereClause = String.format("%s=?", Contract.Suppliers.ID);
        String[] whereArgs = {supplier.getIdSupplier()};
        int result = database.update(DBHelper.Tables.SUPPLIER,
                values, whereClause, whereArgs);
        return result>0;
    }

    public boolean deleteSupplier(String idSupplier) {
        SQLiteDatabase database = db.getWritableDatabase();
        String whereClause = Contract.Suppliers.ID + "=?";
        String[] whereArgs = {idSupplier};
        int result = database.delete(DBHelper.Tables.SUPPLIER,
                whereClause, whereArgs);
        return result > 0;
    }


}
