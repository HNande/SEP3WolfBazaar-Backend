package org.example.application.repository;

import com.google.protobuf.ByteString;
import generated.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.emptyToNull;

public final class ItemListingMapper {
  private static final Map<ImageFileTypeJsonDTO, ImageFileType> FILETYPE_TO_PROTO = new EnumMap<>(Map.of(
      ImageFileTypeJsonDTO.PNG, ImageFileType.PNG,
      ImageFileTypeJsonDTO.JPEG, ImageFileType.JPEG,
      ImageFileTypeJsonDTO.JPG, ImageFileType.JPG));

  private static final Map<ImageFileType, ImageFileTypeJsonDTO> FILETYPE_FROM_PROTO =
      FILETYPE_TO_PROTO.entrySet().stream()
          .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

  public static AddItemListingRequest toRequest(ItemListingJsonDTO dto) {
    AddItemListingRequest.Builder builder = AddItemListingRequest.newBuilder()
        .setItemName(dto.itemName())
        .setDescription(dto.description())
        .setPrice(dto.price());

    if (dto.album() != null) {
      builder.setAlbum(toAlbum(dto.album()));
    }
    return builder.build();
  }
  public static ItemListingResponseDTO toResponse(ItemListing item) {
    return new ItemListingResponseDTO(
        item.getId(),
        item.getItemName(),
        emptyToNull(item.getDescription()),
        item.getPrice(),
        toAlbumResponse(item.getAlbum()),
            item.getDate().getYear(),
            item.getDate().getMonth(),
            item.getDate().getDay());
  }
  public static ItemSummaryResponseDTO toSummaryResponse(ItemSummary item) {
    return new ItemSummaryResponseDTO(
        item.getId(),
        item.getItemName(),
        emptyToNull(item.getDescription()),
        item.getPrice(),
        item.getDate().getYear(),
        item.getDate().getMonth(),
        item.getDate().getDay());
  }

  public static ThumbnailResponseDTO toThumbnailResponse(ItemThumbnail t) {
    return new ThumbnailResponseDTO(
        t.getItemId(),
        FILETYPE_FROM_PROTO.get(t.getFiletype()),
        t.getWidth(),
        t.getHeight(),
        t.getData().toByteArray());
  }

  private static AlbumResponseDTO toAlbumResponse(Album a) {
    return new AlbumResponseDTO(
        a.getTitle(),
        a.getImagesList().stream()
            .map(ItemListingMapper::toImageResponse)
            .toList());
  }
  private static ImageResponseDTO toImageResponse(Image i) {
    return new ImageResponseDTO(
        i.getName(),
        FILETYPE_FROM_PROTO.get(i.getFiletype()),
        i.getWidth(),
        i.getHeight(),
        i.getData().toByteArray());
  }
  private static Album toAlbum(AlbumJsonDTO dto) {
    Album.Builder builder = Album.newBuilder()
        .setTitle(dto.title());

    if (dto.images() != null) {
      builder.addAllImages(
          dto.images().stream()
              .map(ItemListingMapper::toImage)
              .toList());
    }
    return builder.build();
  }

  private static Image toImage(ImageJsonDTO dto) {
    return Image.newBuilder()
        .setName(dto.name())
        .setFiletype(FILETYPE_TO_PROTO.get(dto.filetype()))
        .setWidth(dto.width())
        .setHeight(dto.height())
        .setData(ByteString.copyFrom(dto.data()))
        .build();
  }
}
