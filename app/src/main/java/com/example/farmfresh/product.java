package com.example.farmfresh;

public class product {
    private String name;
    private String category;
    private int price;
    private String origin;
    public product(String name,String category,int price,String origin){
        this.category=category;
        this.name=name;
        this.price=price;
        this.origin=origin;
    }
    public product(){

    }
    public String getName(){ return this.name;}
    public String getCategory(){return this.category;}
    public int getPrice(){return this.price;}
    public String getOrigin(){return this.origin;}

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
