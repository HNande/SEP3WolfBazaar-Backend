package org.example.application.repository;

import generated.Date;
import generated.ItemListing;
import generated.categories;

import java.util.Set;

public record ItemListingResponseDTO(
    int id,
    String itemName,
    String memberName,
    String description,
    int price,
    Set<CategoryDTO> categories,
    AlbumResponseDTO album
    ){
}