package org.example.application.service;

import generated.ItemListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.example.application.repository.ItemDAO;
import org.example.application.repository.ItemList;

@Service
public class ItemListingService {
  private ItemDAO itemDAO;

  @Autowired public ItemListingService(ItemDAO itemDAO) {
    this.itemDAO = itemDAO;
  }

  public ItemList getItemList() {
    return itemDAO.getAllItems();
  }

  public void addItem(ItemListing item, MultipartFile[] photos) {
    itemDAO.addItem(item, photos);
  }

  public ItemListing getItemById(int id) {
    return itemDAO.getItemById(id);
  }
}
