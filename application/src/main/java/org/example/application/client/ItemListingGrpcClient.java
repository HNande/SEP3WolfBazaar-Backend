package org.example.application.client;

import generated.*;
import org.springframework.stereotype.Component;

@Component
public class ItemListingGrpcClient {

  @GrpcClient("item-listing-server")
  private ItemListingServiceGrpc.ItemListingServiceBlockingStub stub;

  public ItemListing addItem(ItemListing item) {
    AddItemListingRequest request = AddItemListingRequest.newBuilder()
        .setItemListing(toProto(item))
        .build();

    ItemListingResponse response = stub.getItemListing(request);
    return toDomain(response.getItemlisting());
  }
}
