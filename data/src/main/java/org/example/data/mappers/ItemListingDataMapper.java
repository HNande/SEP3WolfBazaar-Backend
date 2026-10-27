package org.example.data.mappers;

import com.google.protobuf.ByteString;
import generated.*;
import org.example.data.dto.AlbumDataDTO;
import org.example.data.dto.ImageDataDTO;
import org.example.data.dto.ItemListingDataDTO;
import org.example.data.dto.ItemListingResponseDTO;

import java.util.List;

import static com.google.common.base.Strings.emptyToNull;

public class ItemListingDataMapper {

  //ItemListingDataDTO --> ItemListingDAO.addNewItemListing(ItemListingDataDTO)
  //ItemListing DAO provides ItemID, and Date
  public static ItemListingDataDTO toDomainDTO(AddItemListingRequest request) {
    return new ItemListingDataDTO(
        request.getItemName(),
        request.getMemberName(),
        request.getDescription(),
        request.getPrice(),
        request.getCategoriesList(),
        toAlbumDTO(request.getAlbum()));
  }

  public static ItemListingResponse toResponse(ItemListingResponseDTO dto) {

    return ItemListingResponse.newBuilder()
        .setItemlisting(
            ItemListing.newBuilder()
            .setId(dto.id())
            .setItemName(dto.itemName())
            .setMemberName(dto.memberName())
            .setPrice(dto.price())
            .addAllCategories(dto.categories())
            .setAlbum(toProto(dto.album()))
            .build())
        .build();
  }
  //TODO Before constructing the proto ItemListingResponse we should first turn the ItemListing Entity into a ResponseDTO and then turn the ResponseDTO into the Response the Server uses
  public static ItemListingResponseDTO toResponseDTO(org.example.data.entities.ItemListing itemListing) {

  }
  private static AlbumDataDTO toAlbumDTO(Album album) {
    return new AlbumDataDTO(
        album.getTitle(),
        album.getImagesList().stream()
            .map(ItemListingDataMapper::toImageDTO)
            .toList());
  }
  private static ImageDataDTO toImageDTO(Image image) {
    return new ImageDataDTO(
        image.getName(),
        image.getFiletype().toString(),
        image.getWidth(),
        image.getHeight(),
        image.getData().toByteArray());     // ByteString -> byte[]
  }
  private static Album toProto(AlbumDataDTO dto) {
    Album.Builder builder = Album.newBuilder()
        .setTitle(dto.title());

    if (dto.images() != null) {
      builder.addAllImages(
          dto.images().stream()
              .map(ItemListingDataMapper::toProto)
              .toList());
    }
    return builder.build();
  }

  private static Image toProto(ImageDataDTO dto) {

    return Image.newBuilder()
        .setName(dto.name())
        .setFiletype(ImageFileType.valueOf(dto.filetype()))
        .setWidth(dto.width())
        .setHeight(dto.height())
        .setData(ByteString.copyFrom(dto.data()))
        .build();

  }
}
