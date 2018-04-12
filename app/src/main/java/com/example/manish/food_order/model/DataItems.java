package com.example.manish.food_order.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.manish.food_order.database.ItemsTable;

/**
 * Created by manish on 3/2/2018.
 */

public class DataItems implements Parcelable {

    private String itemName;
    private String category;
    private String description;
    private double price;
    private String image;
    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeDouble(this.price);
        dest.writeString(this.image);

    }

    public DataItems() {
    }

    protected DataItems(Parcel in) {
        this.itemId=in.readString();
        this.itemName = in.readString();
        this.category = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.image = in.readString();
    }

    public static final Creator<DataItems> CREATOR = new Creator<DataItems>() {
        @Override
        public DataItems createFromParcel(Parcel source) {
            return new DataItems(source);
        }

        @Override
        public DataItems[] newArray(int size) {
            return new DataItems[size];
        }
    };



    public ContentValues toValues() {
        ContentValues values = new ContentValues(7);
        values.put(ItemsTable.COLUMN_ID, itemId);
        values.put(ItemsTable.COLUMN_NAME, itemName);
        values.put(ItemsTable.COLUMN_DESCRIPTION, description);
        values.put(ItemsTable.COLUMN_CATEGORY, category);
        values.put(ItemsTable.COLUMN_PRICE, price);
        values.put(ItemsTable.COLUMN_IMAGE, image);
        return values;
    }
}



