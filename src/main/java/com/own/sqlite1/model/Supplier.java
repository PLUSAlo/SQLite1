package com.own.sqlite1.model;

/**
 * Created by alo_m on 04/04/2018.
 */

public class Supplier {
    private String idSupplier;
    private String name;
    private String lastname;
    private String type;

    public Supplier(String idSupplier,String name, String lastname, String type) {
        this.idSupplier = idSupplier;
        this.name = name;
        this.lastname = lastname;
        this.type = type;
    }

    public Supplier() {
        this("","", "", "");
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "idSupplier='" + idSupplier + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

