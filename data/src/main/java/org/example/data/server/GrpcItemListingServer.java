package org.example.data.server;

import generated.AddItemListingRequest;
import generated.GetItemListingRequest;
import generated.ItemListingResponse;
import generated.ItemListingServiceGrpc;
import generated.ListItemListingsRequest;
import generated.ListItemListingsResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.data.dao.ItemListingDAO;
import org.example.data.dto.ItemListingResponseDTO;
import org.example.data.mappers.ItemListingDataMapper;
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
    public void addItemListing(AddItemListingRequest request,
        StreamObserver<ItemListingResponse> responseObserver) {
        try {
            ItemListingResponseDTO created =
                service.addItem(ItemListingDataMapper.toDomainDTO(request));
            responseObserver.onNext(ItemListingDataMapper.toResponse(created));
            responseObserver.onCompleted();
            LOG.info("Item listing [ id={}, name={} ] added successfully",
                created.id(), created.itemName());
        } catch (Exception e) {
            fail(responseObserver, e, "add item listing");
        }
    }

    @Override
    public void getItemListing(GetItemListingRequest request,
        StreamObserver<ItemListingResponse> responseObserver) {
        try {
            Optional<ItemListingResponseDTO> item = service.getItemById(request.getId());
            if (item.isPresent()) {
                responseObserver.onNext(ItemListingDataMapper.toResponse(item.get()));
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Item listing with id " + request.getId() + " not found")
                    .asRuntimeException());
            }
        } catch (Exception e) {
            fail(responseObserver, e, "get item listing");
        }
    }

    @Override
    public void listItemListings(ListItemListingsRequest request,
        StreamObserver<ListItemListingsResponse> responseObserver) {
        try {
            ItemListingDAO.ItemListingPage page =
                service.getItems(request.getPageSize(), request.getPageToken());
            responseObserver.onNext(ListItemListingsResponse.newBuilder()
                .addAllItems(page.items().stream()
                    .map(ItemListingDataMapper::toProtoListing)
                    .toList())
                .setNextPageToken(page.nextPageToken())
                .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            fail(responseObserver, e, "list item listings");
        }
    }

    private void fail(StreamObserver<?> responseObserver, Exception e, String action) {
        if (e instanceof IllegalArgumentException) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                .withDescription(e.getMessage())
                .asRuntimeException());
        } else {
            LOG.error("Could not {}", action, e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Could not " + action)
                .asRuntimeException());
        }
    }
}