package org.example.data.server;

import generated.AddItemListingRequest;
import generated.GetItemListingRequest;
//import generated.ItemListing;
import org.example.data.entities.ItemListing;
import generated.ItemListingResponse;
import generated.ItemListingServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.data.service.DataItemListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;


import java.util.Optional;

@GrpcService
public class GrpcItemListingServer extends ItemListingServiceGrpc.ItemListingServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcItemListingServer.class);
    private final DataItemListingService service;

    public GrpcItemListingServer(DataItemListingService service) {
        this.service = service;
    }

    @Override
    public void addItemListing(AddItemListingRequest request, StreamObserver<ItemListingResponse> responseObserver) {
        try {
            ItemListing savedItem = service.addItem(request.getItemListing());
            ItemListingResponse response = ItemListingResponse.newBuilder()
                    .setItemlisting(savedItem)
                    .build();

            responseObserver.onNext(response);
            LOG.info("\nItem Listing [ id={}, name={} ] added successfully\n", savedItem.getId(), savedItem.getItemName());
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOG.error("Error saving ItemListing to database", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to save item listing: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getItemListing(GetItemListingRequest request, StreamObserver<ItemListingResponse> responseObserver) {
        Optional<ItemListing> itemOpt = service.getItemById(request.getId());
        if (itemOpt.isPresent()) {
            ItemListingResponse response = ItemListingResponse.newBuilder()
                    .setItemlisting(itemOpt.get())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Item listing with id " + request.getId() + " not found")
                    .asRuntimeException());
        }
    }
}