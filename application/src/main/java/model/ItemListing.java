package model;

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

  public ArrayList<BufferedImage> getItemListingImageList() {
    return new ArrayList<>(images);
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

  }

  public void setItemListingMemberName(String name) {

  }

  public void setItemListingDescription(String description) {

  }

  public void setItemListingPrice(int price) {

  }

  public void addItemListingImage(BufferedImage image) {

  }

  public void addItemListingCategory(ItemListingCategory category) {

  }

  public void removeItemListingCategory(int id) {

  }

  public void setItemListingDate(Date date) {

  }
}
