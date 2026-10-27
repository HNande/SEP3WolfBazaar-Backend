package org.example.application.repository;

import generated.AddItemListingRequest;
import generated.ItemListing;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {

  ItemListing addItem(AddItemListingRequest request);

  Optional<ItemListing> getItemById(int id);

  ItemPage getItems(int pageSize, String pageToken);

  List<ItemListing> getAllItems();

  record ItemPage(List<ItemListing> items, String nextPageToken) {
    public boolean hasNext() {
      return nextPageToken != null && !nextPageToken.isEmpty();
    }
  }
}