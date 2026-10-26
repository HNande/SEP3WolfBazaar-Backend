package org.example.data.mappers;

import com.google.protobuf.ByteString;
import generated.AddItemListingRequest;
import generated.Album;
import generated.Image;
import org.example.data.dto.ItemListingDataDTO;
import org.example.data.entities.ItemListing;
import org.example.data.entities.Member;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.emptyToNull;

public class ItemListingDataMapper {
  public static ItemListingDataDTO toEntity(AddItemListingRequest request) {
    ItemListingDataDTO dto = new ItemListingDataDTO((request.getMemberName(),
        request.getItemName(),
        request.getDescription(),
        request.getPrice(),
        request.)
    return dto;
  }
  public static ItemListingResponseDTO toResponse(ItemListing item) {
    Set<CategoryDTO> categories = item.getCategoriesList().stream()
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
}
