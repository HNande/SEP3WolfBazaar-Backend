package org.example.application.repository;

import org.springframework.web.multipart.MultipartFile;

public class ItemFormDTO {
    private String name;
    private String description;
    private int price;
    private MultipartFile[] photos;

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public MultipartFile[] getPhotos() { return photos; }
    public void setPhotos(MultipartFile[] photos) { this.photos = photos; }
}