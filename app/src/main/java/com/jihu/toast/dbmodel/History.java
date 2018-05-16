package com.jihu.toast.dbmodel;

public class History extends Item{



    //constructor
    public History(){
    }

    public History(String name, String reguireTime, String registerTime, String startTime, String completeTime, String memories, String picture){
        this.name=name;
        this.requireTime=reguireTime;
        this.registerTime=registerTime;
        this.startTime=startTime;
        this.completeTime=completeTime;
        this.memories=memories;
        this.picture=picture;
    }

    public History(String name, String reguireTime, String registerTime, String startTime, String completeTime){
        this.name=name;
        this.requireTime=reguireTime;
        this.registerTime=registerTime;
        this.startTime=startTime;
        this.completeTime=completeTime;
        this.memories=null;
        this.picture=null;
    }


    //getter

    public String getStartTime(){
        return this.startTime;
    }

    public String getCompleteTime(){
        return this.completeTime;
    }

    public String getMemories(){
        return this.memories;
    }

    public String getPicture(){
        return this.picture;
    }
}
