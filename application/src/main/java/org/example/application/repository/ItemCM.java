package org.example.application.repository;

import generated.AddItemListingRequest;
import generated.ItemListing;
import generated.ItemSummary;
import generated.ItemThumbnail;

import java.util.List;
import java.util.Optional;

public interface ItemCM {

  ItemListing addItem(AddItemListingRequest request);

  Optional<ItemListing> getItemById(int id);

  ItemPage getItems(int pageSize, String pageToken);

  List<ItemListing> getAllItems();

  List<ItemSummary> getAllItemSummaries();

  List<ItemThumbnail> getThumbnails(List<Integer> ids);

  record ItemPage(List<ItemListing> items, String nextPageToken) {
    public boolean hasNext() {
      return nextPageToken != null && !nextPageToken.isEmpty();
    }
  }
}
