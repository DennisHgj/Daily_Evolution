package com.example.daliyevolution.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/***
 * tb_trasaction
 * design the table part for storage transactions data
 * @Author Guanjie Huang
 * @ID u6532079
 */



@Table(name = "Tb_transaction")
public class Tb_transaction implements Serializable {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "inorout")
    private String inorout;//0-Spend，1-Income
    @Column(name = "amount")
    private double amount;//amount
    @Column(name = "type")
    private String type;//type
    @Column(name = "time")
    private String time;//time
    @Column(name = "label")
    private String label;//label

    public Tb_transaction() {
    }

    public Tb_transaction(String inorout, double amount, String type, String time, String label) {
        this.inorout = inorout;
        this.amount = amount;
        this.type = type;
        this.time = time;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInorout() {
        return inorout;
    }

    public void setInorout(String inorout) {
        this.inorout = inorout;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
