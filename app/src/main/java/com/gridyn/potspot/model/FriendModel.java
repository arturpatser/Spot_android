package com.gridyn.potspot.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gridyn.potspot.BR;
import com.gridyn.potspot.response.friendResponse.Stripe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmytro_vodnik on 8/28/16.
 * working on potspot project
 */
public class FriendModel extends BaseObservable implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("imgs")
    @Expose
    private List<Object> imgs = new ArrayList<Object>();
    @SerializedName("spot")
    @Expose
    private List<Object> spot = new ArrayList<Object>();
    @SerializedName("stripe")
    @Expose
    private Stripe stripe;

    private String id;

    @Bindable
    boolean selected;

    @Bindable
    float splitSize;

    protected FriendModel(Parcel in) {
        name = in.readString();
        email = in.readString();
        country = in.readString();
        about = in.readString();
        birthday = in.readString();
        gender = in.readString();
        id = in.readString();
        selected = in.readByte() != 0;
        splitSize = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(country);
        dest.writeString(about);
        dest.writeString(birthday);
        dest.writeString(gender);
        dest.writeString(id);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeFloat(splitSize);
    }

    @Override
    public int describeContents() {
        return 0;
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

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     *     The about
     */
    public String getAbout() {
        return about;
    }

    /**
     *
     * @param about
     *     The about
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     *
     * @return
     *     The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     *     The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     *
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     *     The imgs
     */
    public List<Object> getImgs() {
        return imgs;
    }

    /**
     *
     * @param imgs
     *     The imgs
     */
    public void setImgs(List<Object> imgs) {
        this.imgs = imgs;
    }

    /**
     *
     * @return
     *     The spot
     */
    public List<Object> getSpot() {
        return spot;
    }

    /**
     *
     * @param spot
     *     The spot
     */
    public void setSpot(List<Object> spot) {
        this.spot = spot;
    }

    /**
     *
     * @return
     *     The stripe
     */
    public Stripe getStripe() {
        return stripe;
    }


    /**
     *
     * @param stripe
     *     The stripe
     */
    public void setStripe(Stripe stripe) {
        this.stripe = stripe;
    }

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
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", about='" + about + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", imgs=" + imgs +
                ", spot=" + spot +
                ", stripe=" + stripe +
                ", id='" + id + '\'' +
                ", selected=" + selected +
                ", splitSize=" + splitSize +
                '}';
    }
}
