package com.hoverhackathon.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TransactionModel {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String name;
    public String status;
    public String date;

    public TransactionModel() {
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
