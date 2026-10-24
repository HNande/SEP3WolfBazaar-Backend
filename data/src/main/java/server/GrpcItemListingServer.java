package server;

import generated.AddItemListingRequest;
import generated.ItemListing;
import generated.ItemListingResponse;
import generated.ItemListingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class GrpcItemListingServer extends ItemListingServiceGrpc.ItemListingServiceImplBase {
  private static final Logger LOG = LoggerFactory.getLogger(GrpcItemListingServer.class.getName());
  private final ItemListingService service;
  public GrpcItemListingServer(ItemListingService service){
    this.service = service;
  }

  public void addItem(AddItemListingRequest request, StreamObserver<ItemListingResponse> responseObserver) {
    var item = service.addItem(request.getItemListing(),request.getItemListing().getAlbum());
    var response = ItemListingResponse.newBuilder()
        .setItemlisting(ItemListingMapper.toProto(item))
        .build();
    responseObserver.onNext(response);
    LOG.info("\nItem Listing  [ {} ] added successfully\n", item);
    responseObserver.onCompleted();
  }
  private static class ItemListingMapper {
    static ItemListing toProto(ItemListing itemListing) {
      return ItemListing.newBuilder()
          .setId(itemListing.getId())
          .setItemName(itemListing.getItemName())
          .build();
    }
  }
}
