package com.gridyn.potspot.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.gridyn.potspot.BR;

/**
 * Created by dmytro_vodnik on 8/28/16.
 * working on potspot project
 */
public class FriendModel extends BaseObservable implements Parcelable {

    private String id;

    @Bindable
    boolean selected;

    String name;

    @Bindable
    float splitSize;

    protected FriendModel(Parcel in) {
        id = in.readString();
        selected = in.readByte() != 0;
        name = in.readString();
        splitSize = in.readFloat();
    }

    public static final Creator<FriendModel> CREATOR = new Creator<FriendModel>() {
        @Override
        public FriendModel createFromParcel(Parcel in) {
            return new FriendModel(in);
        }

        @Override
        public FriendModel[] newArray(int size) {
            return new FriendModel[size];
        }
    };

    public float getSplitSize() {
        return splitSize;
    }

    public void setSplitSize(float splitSize) {
        this.splitSize = splitSize;
        notifyPropertyChanged(BR.splitSize);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(name);
        dest.writeFloat(splitSize);
    }
}
