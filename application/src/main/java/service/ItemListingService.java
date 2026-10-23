package service;

import model.ItemListing;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ItemDAO;
import repository.ItemList;

public class ItemListingService {
  private ItemDAO itemDAO;

  @Autowired public ItemListingService(ItemDAO itemDAO) {
    this.itemDAO = itemDAO;
  }

  public ItemList getItemList() {
    return itemDAO.getAllItems();
  }

  public void addItem(ItemListing item) {
    itemDAO.addItem(item);
  }

  public ItemListing getItemById(int id) {
    return itemDAO.getItemById(id);
  }
}
