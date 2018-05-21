package com.jihu.toast.dbmodel;

public class iCategory {
    int id;
    String cateName;

    //consturctor
    public iCategory(){
    }

    public iCategory(String cateName){
        this.cateName=cateName;
    }

    //setter
    public void setId(int id){
        this.id=id;
    }
    public void setCateName(String cateName){
        this.cateName=cateName;
    }

    //getter

    public int getId(){
        return this.id;
    }
    public String getCateName(){
        return this.cateName;
    }

}
