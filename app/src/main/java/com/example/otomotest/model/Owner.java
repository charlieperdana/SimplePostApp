package com.example.otomotest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class Owner implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("image")
    private String photo;

    @SerializedName("nameTitle")
    private String title;

    @SerializedName("firstName")
    private String name;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("message")
    private String message;

    @SerializedName("itemPhoto")
    private String itemPhoto;

    @SerializedName("expanded")
    private boolean expanded;

    @SerializedName("data")
    private List<Owner> items = null;

    @SerializedName("owner")
    private Owner owner = null;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(String itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public List<Owner> getItems() {
        return items;
    }

    public void setItems(List<Owner> items) {
        this.items = items;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Owner(int id, String photo, String title, String name, String lastName, String message, String itemPhoto) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.name = name;
        this.lastName = lastName;
        this.message = message;
        this.itemPhoto = itemPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.message);
        dest.writeString(this.itemPhoto);
    }

    public Owner() {
    }

    protected Owner(Parcel in) {
        this.id = in.readInt();
        this.photo = in.readString();
        this.title = in.readString();
        this.name = in.readString();
        this.message = in.readString();
        this.itemPhoto = in.readString();
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public Owner(JSONObject object) {


        try {
            String nameTitle = object.getJSONObject("owner").getString("nameTitle");
            String firstName = object.getJSONObject("owner").getString("firstName");
            String lastName = object.getJSONObject("owner").getString("lastName");
            String imgOwner = object.getJSONObject("owner").getString("image");
            String imgItem = object.getString("image");
            String message = object.getString("message");

            String name = firstName + " " +lastName;
            String nameTitlenya = Character.toString(nameTitle.charAt(0)).toUpperCase()+nameTitle.substring(1);

            this.expanded = false;
            this.itemPhoto = imgItem;
            this.photo = imgOwner;
            this.title = nameTitlenya;
            this.message = message;
            this.name = name;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
