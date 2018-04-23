package com.own.sqlite1.model;

/**
 * Created by alo_m on 04/04/2018.
 */

public class SaleDetail {
    private String idSaleHeader;
    private int sequence;
    private String idProduct;
    private int quantity;


    public SaleDetail(String idSaleHeader,int sequence, String idProduct, int quantity) {
        this.idSaleHeader = idSaleHeader;
        this.sequence = sequence;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public SaleDetail(){
        this("",0,"",0);
    }

    public String getIdSaleHeader() {
        return idSaleHeader;
    }

    public void setIdSaleHeader(String idSaleHeader) {
        this.idSaleHeader = idSaleHeader;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SaleDetail{" +
                "idSaleDetail='" + idSaleHeader + '\'' +
                ", sequence=" + sequence +
                ", idProduct='" + idProduct + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
