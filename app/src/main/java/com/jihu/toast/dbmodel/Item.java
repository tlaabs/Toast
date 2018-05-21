package com.jihu.toast.dbmodel;

public class Item {
    int id;
    String name;
    String requireTime=null;
    String registerTime;
    String startTime=null;
    String completeTime=null;
    String memories=null;
    String picture=null;

    public Item(){
    }

    //setter
    public void setId(int id){
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setRequierTime(String requireTime){
        this.requireTime=requireTime;
    }

    public void setRegisterTime(String registerTime){
        this.registerTime=registerTime;
    }
    public void setStartTime(String startTime){
        this.startTime=startTime;
    }

    public void setCompleteTime(String completeTime){
        this.completeTime=completeTime;
    }

    public void setMemories(String memories){
        this.memories=memories;
    }

    public void setPicture(String picture){
        this.picture=picture;
    }

    //getter
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getRequierTime(){
        return this.requireTime;
    }

    public String getRegisterTime(){
        return this.registerTime;
    }
}
