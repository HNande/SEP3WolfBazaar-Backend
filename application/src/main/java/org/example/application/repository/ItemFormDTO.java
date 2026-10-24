package org.example.application.repository;

import generated.categories;
import org.springframework.web.multipart.MultipartFile;

public class ItemFormDTO {
    private int id;
    private String itemName;
    private String memberName;
    private String description;
    private int price;
    //I'm sure that this should be Album album, I don't think the class should hold the MultipartFile[] as its instance variable
    private MultipartFile[] album;
    private categories categories;
    private int year;
    private int month;
    private int day;


    // Геттеры и сеттеры
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public MultipartFile[] getAlbum() { return album; }
    public void setAlbum(MultipartFile[] album) { this.album = album; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public categories getCategories() {
        return categories;
    }

    public void setCategories(categories categories) {
        this.categories = categories;
    }
}