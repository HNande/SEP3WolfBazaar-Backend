package org.example.data.mappers;

import com.google.protobuf.ByteString;
import generated.Album;
import generated.AddItemListingRequest;
import generated.Category;
import generated.Date;
import generated.Image;
import generated.ImageFileType;
import generated.ItemListing;
import generated.ItemListingResponse;
import org.example.data.dto.AlbumDataDTO;
import org.example.data.dto.ImageDataDTO;
import org.example.data.dto.ItemListingDataDTO;
import org.example.data.dto.ItemListingResponseDTO;
import org.example.data.entities.ItemPicture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemListingDataMapper {

  private static final Logger LOG = LoggerFactory.getLogger(ItemListingDataMapper.class);

  // The DAO provides the item id and the date
  public static ItemListingDataDTO toDomainDTO(AddItemListingRequest request) {
    return new ItemListingDataDTO(
        request.getItemName(),
        request.getMemberName(),
        request.getDescription(),
        request.getPrice(),
        request.getCategoriesList(),
        toAlbumDTO(request.getAlbum()));
  }

  public static ItemListingResponseDTO toResponseDTO(
      org.example.data.entities.ItemListing itemListing) {
    LocalDate date = itemListing.getListDate();
    return new ItemListingResponseDTO(
        itemListing.getItemId(),
        itemListing.getItemName(),
        itemListing.getMember().getFullName(),
        itemListing.getDescription(),
        itemListing.getPrice(),
        toProtoCategories(itemListing.getCategories()),
        toAlbumDTO(itemListing.getItemName(), itemListing.getPictures()),
        date.getYear(),
        date.getMonthValue(),
        date.getDayOfMonth());
  }

  private static List<Category> toProtoCategories(
      Set<org.example.data.entities.Category> categories) {
    List<Category> result = new ArrayList<>();
    for (org.example.data.entities.Category category : categories) {
      try {
        result.add(Category.valueOf(category.getName()));
      } catch (IllegalArgumentException e) {
        LOG.warn("Unknown category '{}' in database, skipping it", category.getName());
      }
    }
    return result;
  }

  private static AlbumDataDTO toAlbumDTO(String itemName, List<ItemPicture> pictures) {
    List<ImageDataDTO> images = pictures.stream()
        .map(ItemListingDataMapper::toDTO)
        .toList();
    return new AlbumDataDTO(itemName + " Album", images);
  }

  public static ImageDataDTO toDTO(ItemPicture picture) {
    String fileType = picture.getFileType() != null ? picture.getFileType().name() : "PNG";
    try {
      byte[] fileData = Files.readAllBytes(Paths.get(picture.getFilePath()));

      int width = 0;
      int height = 0;
      BufferedImage decoded = ImageIO.read(new ByteArrayInputStream(fileData));
      if (decoded != null) {
        width = decoded.getWidth();
        height = decoded.getHeight();
      }
      return new ImageDataDTO(picture.getFileName(), fileType, width, height, fileData);

    } catch (Exception e) {
      LOG.error("Could not read image file {}", picture.getFilePath(), e);
      return new ImageDataDTO(picture.getFileName(), fileType, 0, 0, new byte[0]);
    }
  }

  public static ItemListingResponse toResponse(ItemListingResponseDTO dto) {
    return ItemListingResponse.newBuilder()
        .setItemlisting(toProtoListing(dto))
        .build();
  }

  public static ItemListing toProtoListing(ItemListingResponseDTO dto) {
    return ItemListing.newBuilder()
        .setId(dto.id())
        .setItemName(orEmpty(dto.itemName()))
        .setMemberName(orEmpty(dto.memberName()))
        .setDescription(orEmpty(dto.description()))
        .setPrice(dto.price())
        .addAllCategories(dto.categories())
        .setAlbum(toProto(dto.album()))
        .setDate(Date.newBuilder()
            .setYear(dto.year())
            .setMonth(dto.month())
            .setDay(dto.day())
            .build())
        .build();
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
        image.getData().toByteArray());
  }

  private static Album toProto(AlbumDataDTO dto) {
    Album.Builder builder = Album.newBuilder()
        .setTitle(orEmpty(dto.title()));

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
        .setName(orEmpty(dto.name()))
        .setFiletype(ImageFileType.valueOf(dto.filetype()))
        .setWidth(dto.width())
        .setHeight(dto.height())
        .setData(ByteString.copyFrom(dto.data()))
        .build();
  }

  private static String orEmpty(String value) {
    return value == null ? "" : value;
  }
}