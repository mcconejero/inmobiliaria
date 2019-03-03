package com.triana.salesianos.inmobilimario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
import java.util.Locale;
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301

public class PostResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photos")
    @Expose
    private String[] photos;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("rooms")
    @Expose
    private int rooms;
    @SerializedName("size")
    @Expose
    private double size;
    @SerializedName("categoryId")
    @Expose
    private CategoryResponse categoryId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("favs")
    @Expose
    private List<String> favs = new ArrayList<>();
    @SerializedName("photos")
    @Expose
    private List<String> photos = new ArrayList<>();

    public PostResponse() {
    }

<<<<<<< HEAD
    public PostResponse(String id, String title, String description, double price, int rooms, double size, CategoryResponse categoryId, String address, String zipcode, String city, String province, String loc, String createdAt, String updatedAt, List<String> favs, List<String> photos) {
=======
    public PostResponse(String id, String title, String[] photos, String description, double price, int rooms, double size, CategoryResponse categoryId, String address, String zipcode, String city, String province, String loc, String createdAt, String updatedAt) {
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
        this.id = id;
        this.title = title;
        this.photos = photos;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.size = size;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favs = favs;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getPhotos() {
        return photos;
    }

    public PostResponse setPhotos(String[] photos) {
        this.photos = photos;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public CategoryResponse getCategoryId() {
        return categoryId;
    }

<<<<<<< HEAD
    public void setCategoryId(CategoryResponse categoryId) {
=======
    public PostResponse setCategoryId(CategoryResponse categoryId) {
>>>>>>> b0d635f599e5af8c945e3afd1296b091c4fc0301
        this.categoryId = categoryId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getFavs() {
        return favs;
    }

    public void setFavs(List<String> favs) {
        this.favs = favs;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
