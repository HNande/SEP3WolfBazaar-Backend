package org.example.data.mappers;

import com.google.protobuf.ByteString;
import generated.AddItemListingRequest;
import generated.Album;
import generated.Image;
import org.example.data.dto.AlbumDataDTO;
import org.example.data.dto.ImageDataDTO;
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
    ItemListingDataDTO dto = new ItemListingDataDTO(request.getMemberName(),
        request.getItemName(),
        request.getDescription(),
        request.getPrice(),
        request.getAlbum().getImagesList())
    return dto;
  }
  public static ItemListingDataResponseDTO toResponse(ItemListing item) {

    return new ItemListingResponseDTO(
        item.getId(),
        item.getItemName(),
        item.getMemberName(),
        emptyToNull(item.getDescription()),
        item.getPrice(),
        categories,
        toAlbumResponse(item.getAlbum()));
  }
  private static AlbumDataDTO toAlbumDTO(Album a) {
    return new AlbumDataDTO(
        a.getTitle(),
        a.getImagesList().stream()
            .map(ItemListingDataMapper::toImageEntity)
            .toList());
  }
  private static ImageDataDTO toImageDTO(Image i) {
    return new ImageDataDTO(
        i.getName(),
        i.getFiletype().toString(),
        i.getWidth(),
        i.getHeight(),
        i.getData().toByteArray());     // ByteString -> byte[]
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
        .setFiletype((dto.filetype().toString())
        .setWeight(dto.width())
        .setHeight(dto.height())
        .setData(ByteString.copyFrom(dto.data()))
        .build();
  }
}
