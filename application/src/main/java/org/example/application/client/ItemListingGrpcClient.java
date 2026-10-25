package org.example.application.client;

import generated.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.application.repository.ItemListingMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemListingGrpcClient {

  @GrpcClient("item-listing-server")
  private ItemListingServiceGrpc.ItemListingServiceBlockingStub stub;

  public ItemListing addItem(AddItemListingRequest request) {

    return stub.addItemListing(request).getItemlisting();
  }
  public ItemListing getItem(int id) {
    GetItemListingRequest request = GetItemListingRequest.newBuilder()
        .setId(id)
        .build();

    return stub.getItemListing(request).getItemlisting();
  }
}
