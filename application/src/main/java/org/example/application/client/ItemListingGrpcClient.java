package org.example.application.client;

import generated.AddItemListingRequest;
import generated.GetItemListingRequest;
import generated.ItemListing;
import generated.ItemListingResponse;
import org.example.application.ItemListingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.application.repository.ItemListingJsonDTO;
import org.example.application.repository.ItemListingMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemListingGrpcClient {

    @GrpcClient("item-listing-server")
    private ItemListingServiceGrpc.ItemListingServiceBlockingStub stub;

    public ItemListing addItem(ItemListingJsonDTO item) {

        AddItemListingRequest request = ItemListingMapper.toRequest(item);

    return stub.addItemListing(request).getItemlisting();
  }
  //Double whammy methods
  public ItemListing getItem(int id) {
    GetItemListingRequest request = GetItemListingRequest.newBuilder()
        .setId(id)
        .build();

        ItemListingResponse response = stub.getItemListing(request);
        return response.getItemlisting();
    }

}