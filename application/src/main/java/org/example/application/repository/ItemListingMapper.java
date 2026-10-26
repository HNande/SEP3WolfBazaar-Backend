package org.example.application.repository;

import com.google.protobuf.ByteString;
import generated.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.emptyToNull;

public final class ItemListingMapper {
  private static final Map<CategoryJsonDTO, Category> CATEGORY_TO_PROTO = Map.of(
      CategoryJsonDTO.CLOTHING, Category.CLOTHING,
      CategoryJsonDTO.ACCESSORIES, Category.ACCESSORIES,
      CategoryJsonDTO.CURIOS,   Category.CURIOS);

  private static final Map<Category, CategoryJsonDTO> CATEGORY_FROM_PROTO =
      CATEGORY_TO_PROTO.entrySet().stream()
          .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

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
        .setMemberName(dto.memberName())
        .setDescription(dto.description())
        .setPrice(dto.price())
        .addAllCategories(toProtoCategories(dto.categories()));

    if (dto.album() != null) {
      builder.setAlbum(toAlbum(dto.album()));
    }
    return builder.build();
  }
  public static ItemListingResponseDTO toResponse(ItemListing item) {
    Set<CategoryJsonDTO> categories = item.getCategoriesList().stream()
        .map(CATEGORY_FROM_PROTO::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toCollection(LinkedHashSet::new));

    return new ItemListingResponseDTO(
        item.getId(),
        item.getItemName(),
        item.getMemberName(),
        emptyToNull(item.getDescription()),
        item.getPrice(),
        categories,
        toAlbumResponse(item.getAlbum()));
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
        i.getData().toByteArray());     // ByteString -> byte[]
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
  private static List<Category> toProtoCategories(Set<CategoryJsonDTO> categories) {
    return categories.stream()
        .map(CATEGORY_TO_PROTO::get)
        .toList();
  }
}
