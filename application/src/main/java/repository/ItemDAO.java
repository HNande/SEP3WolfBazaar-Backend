package repository;

import model.ItemListing;
import org.springframework.stereotype.Repository;

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

  public void addItem(ItemListing item) {
    item.setItemListingId(items.getItemList().size() + 1);
    items.getItemList().add(item);
  }
}
