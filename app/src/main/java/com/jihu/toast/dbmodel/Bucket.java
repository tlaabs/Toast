package com.jihu.toast.dbmodel;

public class Bucket extends Item{

    //constructor
    public Bucket(){
    }

    public Bucket(String name, String reguireTime, String registerTime){
        this.name=name;
        this.requireTime=reguireTime;
        this.registerTime=registerTime;
    }

    public Bucket(String name, String registerTime){
        this.name=name;
        this.requireTime=null;
        this.registerTime=registerTime;
    }
}
