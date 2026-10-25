package org.example.application.service;

import generated.AddItemListingRequest;
import generated.ItemListing;
import org.example.application.client.ItemListingGrpcClient;
import org.example.application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemListingService {
  private final ItemListingGrpcClient client;
  private ItemDAO itemDAO;

  @Autowired
  public ItemListingService(ItemListingGrpcClient client) {
    this.client = client;
  }
  public ItemListingResponseDTO addItem(ItemListingJsonDTO dto) {
    AddItemListingRequest request = ItemListingMapper.toRequest(dto);
    ItemListing saved = client.addItem(request);
    return ItemListingMapper.toResponse(saved);
  }

  public ItemListingResponseDTO getItemById(int id) {
    return ItemListingMapper.toResponse(client.getItem(id));
  }
  public ItemList getItemList() {
    return itemDAO.getAllItems();
  }

// public void addItem(ItemListing item) {
//   itemDAO.addItem(item);
// }

//public ItemListing getItemById(int id) {
//  return itemDAO.getItemById(id);
//}
}
