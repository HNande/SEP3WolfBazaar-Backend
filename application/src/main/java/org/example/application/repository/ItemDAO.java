package org.example.application.repository;

import org.example.application.model.ItemListing;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository public class ItemDAO {
  private static ItemList items = new ItemList();

  static {
    items.getItemList().add(new ItemListing(1));
  }

  public ItemList getAllItems() {
    return items;
  }

  public ItemListing getItemById(int id) {
    return items.getItemList().stream().filter(i -> i.getItemListingId() == id)
        .findFirst().orElse(null);
  }

  public void addItem(ItemListing item, MultipartFile[] photos) {
    item.setItemListingId(items.getItemList().size() + 1);
    item.setPhotos(photos);
    items.getItemList().add(item);
  }
}
