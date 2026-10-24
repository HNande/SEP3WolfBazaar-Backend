package org.example.application.repository;

import generated.Album;
import generated.Date;
import generated.ItemListing;
import generated.categories;

public record ItemResponseDTO(
    int id,
    String itemName,
    String memberName,
    String description,
    int price,
    Album album,
    java.util.List<categories> categories,
    Date date){

    public static ItemResponseDTO from(ItemListing item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getItemName(),
                item.getMemberName(),
                item.getDescription(),
                item.getPrice(),
                item.getAlbum(),
                item.getCategoriesList(),
                item.getDate()
        );
    }
}