package com.graodavilla.app.data;

public class Product {
    public final String name;
    public final int price;           //em R$
    public final int imageResId;      //R.drawable.xxx
    public final String description;  //texto do detalhe

    public Product(String name, int price, int imageResId, String description) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
    }
}
