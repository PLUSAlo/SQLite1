package com.own.sqlite1.model;

import java.io.Serializable;

/**
 * Created by alo_m on 04/04/2018.
 */

public class SaleHeader implements Serializable {
    private String idSaleHeader;
    private String date;
    private int total;
    private String idSupplier;
    private String idMethodPayment;


    public SaleHeader(String idSaleHeader, String date, int total, String idMethodPayment, String idSupplier) {
        this.idSaleHeader = idSaleHeader;
        this.idSupplier = idSupplier;
        this.idMethodPayment = idMethodPayment;
        this.date = date;
        this.total = total;
    }

    public SaleHeader(){this("","",0,"","");}

    public String getIdSaleHeader() {
        return idSaleHeader;
    }

    public void setIdSaleHeader(String idSaleHeader) {
        this.idSaleHeader = idSaleHeader;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getIdMethodPayment() {
        return idMethodPayment;
    }

    public void setIdMethodPayment(String idMethodPayment) {
        this.idMethodPayment = idMethodPayment;
    }

    @Override
    public String toString() {
        return "SaleHeader{" +
                "idSaleHeader='" + idSaleHeader + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                ", idSupplier='" + idSupplier + '\'' +
                ", idMethodPayment='" + idMethodPayment + '\'' +
                '}';
    }
}
