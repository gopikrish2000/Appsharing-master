package com.rbricks.appsharing.newconcept.centerrecyclerview;

public class CenterRecyclerViewItem {
    private String name;
    private boolean isPaddingItem;

    public CenterRecyclerViewItem(String name) {
        this.name = name;
    }

    public CenterRecyclerViewItem(boolean isPaddingItem) {
        this.isPaddingItem = isPaddingItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPaddingItem() {
        return isPaddingItem;
    }

    public void setPaddingItem(boolean paddingItem) {
        isPaddingItem = paddingItem;
    }
}