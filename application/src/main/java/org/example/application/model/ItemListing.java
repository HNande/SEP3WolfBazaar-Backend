package org.example.application.model;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class ItemListing implements ItemListingModel {
  private int itemId;
  private String itemName;
  private String memberName;
  private String description;
  private int price;
  private ArrayList<BufferedImage> images;
  private ArrayList<ItemListingCategory> categories;
  private MultipartFile[] photos;

  public ItemListing(int itemId) {
    this.itemId = itemId;
  }

  public int getItemListingId() {
    return itemId;
  }

  public String getItemListingName() {
    return itemName;
  }

  public String getItemListingMemberName() {
    return memberName;
  }

  public String getItemListingDescription() {
    return description;
  }

  public int getItemListingPrice() {
    return price;
  }

  public MultipartFile[] getPhotos() {
    return photos;
  }

  public ArrayList<BufferedImage> getItemListingImageList() {
    return images != null ? new ArrayList<>(images) : new ArrayList<>();
  }

  public BufferedImage getItemListingImageById(int id) {
    return null;
  }

  public ArrayList<String> getItemListingCategories() {
    return null;
  }

  public String getItemListingCategoryById(int id) {
    return "";
  }

  public Date getItemListingDate() {
    return null;
  }

  public void setItemListingId(int id) {
    itemId = id;
  }

  public void setItemListingName(String name) {
      this.itemName = name;
  }

  public void setItemListingMemberName(String name) {
      this.memberName = name;
  }

  public void setItemListingDescription(String description) {
      this.description = description;
  }

  public void setItemListingPrice(int price) {
      this.price = price;
  }

  public void setPhotos(MultipartFile[] photos) {
    this.photos = photos;
  }

  public void addItemListingImage(BufferedImage image) {
    if (this.images == null) {
      this.images = new ArrayList<>();
    }
    this.images.add(image);
  }

  public void addItemListingCategory(ItemListingCategory category) {
    if (this.categories == null) {
      this.categories = new ArrayList<>();
    }
    this.categories.add(category);
  }

  public void removeItemListingCategory(int id) {

  }

  public void setItemListingDate(Date date) {

  }
}
