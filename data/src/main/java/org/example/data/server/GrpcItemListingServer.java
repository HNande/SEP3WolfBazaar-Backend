package org.example.data.server;

import generated.AddItemListingRequest;
import generated.GetItemListingRequest;
import generated.ItemListing;
import generated.ItemListingResponse;
import generated.ItemListingServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.data.dto.ItemListingDataDTO;
import org.example.data.dto.ItemListingResponseDTO;
import org.example.data.service.DataItemListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;


import java.util.Optional;

import static org.example.data.mappers.ItemListingDataMapper.*;

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
            ItemListingDataDTO dto = toDomainDTO(request);
            ItemListingResponseDTO responseDto= service.addItem(dto);
            ItemListingResponse response = toResponse(responseDto);

            responseObserver.onNext(response);
            LOG.info("\nItem Listing [ id={}, name={} ] added successfully\n", responseDto.id(), responseDto.itemName());
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