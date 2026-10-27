package org.example.application.repository;

import generated.AddItemListingRequest;
import generated.GetItemListingRequest;
import generated.ItemListing;
import generated.ItemListingServiceGrpc.ItemListingServiceBlockingStub;
import generated.ListItemListingsRequest;
import generated.ListItemListingsResponse;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class GrpcItemDAO implements ItemDAO {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcItemDAO.class);
    private static final long TIMEOUT_SECONDS = 10;
    private static final int DEFAULT_PAGE_SIZE = 50;

    private final ItemListingServiceBlockingStub stub;

    public GrpcItemDAO(ItemListingServiceBlockingStub stub) {
        this.stub = stub;
    }

    @Override
    public ItemListing addItem(AddItemListingRequest request) {
        try {
            return stub.withDeadlineAfter(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .addItemListing(request)
                    .getItemlisting();

        } catch (StatusRuntimeException e) {
            LOG.error("Could not add item. gRPC status: {}", e.getStatus());
            throw new RuntimeException("Could not add item: " + e.getStatus().getCode(), e);
        }
    }

    @Override
    public Optional<ItemListing> getItemById(int id) {
        try {
            GetItemListingRequest request = GetItemListingRequest.newBuilder()
                    .setId(id)
                    .build();

            ItemListing item = stub.withDeadlineAfter(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .getItemListing(request)
                    .getItemlisting();

            return Optional.of(item);

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Optional.empty();
            }

            LOG.error("Could not get item {}. gRPC status: {}", id, e.getStatus());
            throw new RuntimeException("Could not get item " + id + ": " + e.getStatus().getCode(), e);
        }
    }

    @Override
    public ItemPage getItems(int pageSize, String pageToken) {
        try {
            ListItemListingsRequest request = ListItemListingsRequest.newBuilder()
                    .setPageSize(pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE)
                    .setPageToken(pageToken == null ? "" : pageToken)
                    .build();

            ListItemListingsResponse response = stub.withDeadlineAfter(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .listItemListings(request);

            return new ItemPage(response.getItemsList(), response.getNextPageToken());

        } catch (StatusRuntimeException e) {
            LOG.error("Could not list items. gRPC status: {}", e.getStatus());
            throw new RuntimeException("Could not list items: " + e.getStatus().getCode(), e);
        }
    }

    @Override
    public List<ItemListing> getAllItems() {
        List<ItemListing> allItems = new ArrayList<>();
        String token = "";
        do {
            ItemPage page = getItems(DEFAULT_PAGE_SIZE, token);
            allItems.addAll(page.items());
            token = page.nextPageToken();
        } while (token != null && !token.isEmpty());

        return allItems;
    }
}