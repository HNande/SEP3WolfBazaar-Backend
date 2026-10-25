package org.example.application.client;

import generated.AddItemListingRequest;
import generated.GetItemListingRequest;
import generated.ItemListing;
import generated.ItemListingResponse;
import generated.ItemListingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.application.repository.ItemListingMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemListingGrpcClient {

    @GrpcClient("item-listing-server")
    private ItemListingServiceGrpc.ItemListingServiceBlockingStub stub;

    public ItemListing addItem(ItemListing item) {
        AddItemListingRequest request = AddItemListingRequest.newBuilder()
                .setItemListing(item)
                .build();

    return stub.addItemListing(request).getItemlisting();
  }
  public ItemListing getItem(int id) {
    GetItemListingRequest request = GetItemListingRequest.newBuilder()
        .setId(id)
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