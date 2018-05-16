package com.jihu.toast.dbmodel;

public class Inprogress extends Item{


    //constructor
    public Inprogress(){
    }

    public Inprogress(String name, String reguireTime, String registerTime, String startTime){
        this.name=name;
        this.requireTime=reguireTime;
        this.registerTime=registerTime;
        this.startTime=startTime;
    }


    public Inprogress(String name, String registerTime, String startTime){
        this.name=name;
        this.requireTime=null;
        this.registerTime=registerTime;
        this.startTime=startTime;
    }

    //getter

    public String getStartTime(){
        return this.startTime;
    }
}
