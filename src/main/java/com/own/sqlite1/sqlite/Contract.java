package com.own.sqlite1.sqlite;

import java.util.UUID;

/**
 * Created by devtacho on 9/03/18.
 */

public class Contract {
    interface OrderHeaderColumns{
        String ID = "id";
        String ID_CUSTOMER = "id_customer";
        String ID_METHOD_PAYMENT = "id_method_payment";
        String DATE = "record_date";
    }

    interface SaleHeaderColumns{
        String ID = "id";
        String ID_SUPPLIER = "id_supplier";
        String ID_METHOD_PAYMENT = "id_method_payment";
        String DATE = "record_date";
        String TOTAL= "total";
    }

    interface OrderDetailColumns{
        String ID = "id";
        String SEQUENCE = "sequence";
        String ID_PRODUCT = "id_product";
        String QUANTITY = "quantity";
        String PRICE = "price";
    }

    interface SaleDetailColumns{
        String ID = "id";
        String SEQUENCE = "sequence";
        String ID_PRODUCT = "id_product";
        String QUANTITY = "quantity";
    }

    interface ProductColumns{
        String ID = "id";
        String NAME = "name";
        String PRICE = "price";
        String STOCK = "stock";
    }

    interface MovieColumns{
        String ID = "id";
        String NAME = "name";
        String DIRECTOR = "director";
        String CLASIFICATION = "clasification";
        String YEAR = "year";
        String DURATION = "duration";
    }

    interface  CustomerColumns{
        String ID = "id";
        String FIRSTNAME = "firstname";
        String LASTNAME = "lastname";
        String PHONE = "phone";
    }

    interface MethodPaymentColumns{
        String ID = "id";
        String NAME = "name";
    }

    interface SupplierColumns{
        String ID = "id";
        String NAME = "name";
        String LASTNAME = "lastname";
        String TYPE = "type";
    }


    public static class OrderHeaders implements OrderHeaderColumns{
        public static String generateIdOrderHeader(){
            return  "OH-"+ UUID.randomUUID().toString();
        }
    }

    public static class SaleHeaders implements SaleHeaderColumns{
        public static String generateIdSaleHeader(){
            return  "SH-"+ UUID.randomUUID().toString();
        }
    }
    public static class OrderDetails implements OrderDetailColumns{

    }
    public static class SaleDetails implements SaleDetailColumns{

    }
    public static class Suppliers implements SupplierColumns{
        public static String generateIdSupplier(){
            return  "SP-"+ UUID.randomUUID().toString();
        }
    }


    public static class Products implements ProductColumns{
        public static String generateIdProduct(){
            return  "PR-"+ UUID.randomUUID().toString();
        }
    }

    public static class Movies implements MovieColumns{
        public static String generateIdMovie(){
            return  "MV-"+ UUID.randomUUID().toString();
        }
    }

    public static class Customers implements CustomerColumns{
        public static String generateIdCustomer(){
            return  "CU-"+ UUID.randomUUID().toString();
        }
    }

    public static class MethodsPayment implements MethodPaymentColumns{
        public static String generateIdMethodPayment(){
            return  "MP-"+ UUID.randomUUID().toString();
        }
    }


    private Contract() {

    }


}
