package io.github.tlaabs.toast_sy;

import java.io.Serializable;

/**
 * Created by devsimMe on 2018-05-15.
 */

public class BucketItem implements Serializable{
    int id;
    String title;
    String category;
    String usageTime;
    String regTime;
    String startTime;
    int state;
    String endTime;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getUsageTime() {
        return usageTime;
    }

    public String getRegTime() {
        return regTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getState() {
        return state;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUsageTime(String usageTime) {
        this.usageTime = usageTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
