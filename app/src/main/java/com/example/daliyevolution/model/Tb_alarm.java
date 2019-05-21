package com.example.daliyevolution.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/***
 * tb_alarm
 * design the table part for store alarm data
 * @author Lingyu Xia
 *
 * @ID u6483756
 */

@Table(name = "Tb_alarm")
public class Tb_alarm implements Serializable {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "hour_of_day")
    private int hour_of_day;
    @Column(name = "minute")
    private int minute;

    public Tb_alarm(){}

    public Tb_alarm(int hour_of_day, int minute){
        this.hour_of_day = hour_of_day;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour_of_day() {
        return hour_of_day;
    }

    public void setHour_of_day(int hour_of_day) {
        this.hour_of_day = hour_of_day;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
