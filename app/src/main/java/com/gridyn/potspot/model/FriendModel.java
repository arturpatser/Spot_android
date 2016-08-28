package com.gridyn.potspot.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.gridyn.potspot.BR;

/**
 * Created by dmytro_vodnik on 8/28/16.
 * working on potspot project
 */
public class FriendModel extends BaseObservable {

    private String id;

    @Bindable
    boolean selected;

    String name;

    public FriendModel(String id, boolean selected, String name) {
        this.id = id;
        this.selected = selected;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FriendModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                '}';
    }
}
