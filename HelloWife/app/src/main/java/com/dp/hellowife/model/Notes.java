package com.dp.hellowife.model;

import java.io.Serializable;

/**
 * Created by pradeepd on 09-01-2016.
 */
public class Notes implements Serializable{

    String title;
    String body;
    public boolean isChecked;

    public Notes(){}

    public Notes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
