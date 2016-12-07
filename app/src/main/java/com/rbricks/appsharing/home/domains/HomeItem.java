package com.rbricks.appsharing.home.domains;

/**
 * Created by gopikrishna on 08/12/16.
 */

public class HomeItem {
    public String title;
    public String description;
    public Class activityClass;

    public HomeItem(String title, Class activityClass) {
        this.title = title;
        this.activityClass = activityClass;
    }

    public HomeItem() {
    }
}
