package com.example.daliyevolution.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/***
 * tb_notebook
 * design the table part for storage notebook data
 * @Author Guanjie Huang
 * @ID u6532079
 */

@Table(name = "Tb_notebook")
public class Tb_notebook implements Serializable{
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "label")
    private String label;//Label
    @Column(name = "date")
    private String date;//date
    @Column(name = "content")
    private String content;//content

    public Tb_notebook(){}

    public Tb_notebook(String label, String date, String content) {
        this.label = label;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
