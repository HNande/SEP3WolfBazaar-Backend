package org.example.data.mappers;

import com.google.protobuf.ByteString;
import generated.*;
import org.example.data.dto.AlbumDataDTO;
import org.example.data.dto.ImageDataDTO;
import org.example.data.dto.ItemListingDataDTO;
import org.example.data.dto.ItemListingResponseDTO;
import org.example.data.entities.ItemPicture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

  //It’s okay overall, but it comes with some crutch.
  public static ItemListingResponseDTO toResponseDTO(org.example.data.entities.ItemListing itemListing) {
    return new ItemListingResponseDTO(
            itemListing.getItemId(),
            itemListing.getItemName(),
            itemListing.getMember().getFullName(),
            itemListing.getDescription(),
            itemListing.getPrice(),
            fromSetToList(itemListing.getCategories()),
            fromListToAlbonDTO(itemListing.getPictures()),
            itemListing.getListDate().getYear(),
            itemListing.getListDate().getMonthValue(),
            itemListing.getListDate().getDayOfMonth()
    );
  }

  // I think it’s generally clear why this is needed. But just in case - the issue is that the data types don’t match.
  // Its good unless we want to make an adapter later for sets(as currently db only accepts lists), which I don't wanna
  private static <T> List<Category> fromSetToList(T set) {
    return new ArrayList<>((Integer) set);
  }
// There should be no case that an received album does not have a name, as it should inherit the name from the created itemlisting + "album",
//so for Itemlisting.name = Hamburger, Itemlisting.albumTitle = Hamburger Album
  private static <T> AlbumDataDTO fromListToAlbonDTO(List<ItemPicture> list) {
    Random random = new Random();
    int randomValue = random.nextInt(10000);
    String randomAlbumName = "" + randomValue;
    List<ImageDataDTO> neededList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      neededList.add(toDTO(list.get(i)));
    }
    return new AlbumDataDTO( randomAlbumName, neededList);
  }

  public static ImageDataDTO toDTO(ItemPicture picture) {
    if (picture == null) {
      return null;
    }

    try {
      byte[] fileData = Files.readAllBytes(Paths.get(picture.getFilePath()));

      File file = new File(picture.getFilePath());
      BufferedImage bufferedImage = ImageIO.read(file);

      int width = 0;
      int height = 0;

      if (bufferedImage != null) {
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
      }

      return new ImageDataDTO(
              picture.getFileName(),
              picture.getFileType().name(),
              width,
              height,
              fileData
      );

    } catch (Exception e) {
      //Haha if there is an issue it will create an imagedto with no width or height I like this
      return new ImageDataDTO(
              picture.getFileName(),
              picture.getFileType() != null ? picture.getFileType().name() : "UNKNOWN",
              0,
              0,
              new byte[0]
      );
    }
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
