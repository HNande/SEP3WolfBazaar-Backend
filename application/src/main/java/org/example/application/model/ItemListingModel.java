package org.example.application.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public interface ItemListingModel {
  int getItemListingId();
  String getItemListingName();
  String getItemListingMemberName();
  String getItemListingDescription();
  int getItemListingPrice();
  ArrayList<BufferedImage> getItemListingImageList();
  BufferedImage getItemListingImageById(int id);
  ArrayList<String> getItemListingCategories();
  String getItemListingCategoryById(int id);
  Date getItemListingDate();
  void setItemListingId(int id);
  void setItemListingName(String name);
  void setItemListingMemberName(String name);
  void setItemListingDescription(String description);
  void setItemListingPrice(int price);
  void addItemListingImage(BufferedImage image);
  void addItemListingCategory(ItemListingCategory category);
  void removeItemListingCategory(int id);
  void setItemListingDate(Date date);

}
