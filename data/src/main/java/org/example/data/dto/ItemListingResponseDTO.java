package org.example.data.dto;

import generated.Category;

import java.util.List;
import java.util.Set;

public record ItemListingResponseDTO(
    int id,
    String itemName,
    String memberName,
    String description,
    int price,
    List<Category> categories,
    AlbumDataDTO album

) {

}
