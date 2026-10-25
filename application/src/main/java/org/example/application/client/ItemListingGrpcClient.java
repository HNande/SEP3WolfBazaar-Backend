package org.example.application.client;

import generated.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.application.repository.ItemList;
import org.springframework.stereotype.Component;

@Component
public class ItemListingGrpcClient {

  @GrpcClient("item-listing-server")
  private ItemListingServiceGrpc.ItemListingServiceBlockingStub stub;

  public ItemListing addItem(ItemListing item) {
    AddItemListingRequest request = AddItemListingRequest.newBuilder()
        .setItemListing(item)
        .build();

    ItemListingResponse response = stub.addItemListing(request);
    return response.getItemlisting();
  }
  public ItemListing getItem(int id) {
    GetItemListingRequest request = GetItemListingRequest.newBuilder()
        .setId(id)
        .build();

    return stub.getItemListing(request).getItemlisting();
  }
}
