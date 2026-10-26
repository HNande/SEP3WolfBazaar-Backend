package org.example.data.dto;

import generated.Category;
import java.util.List;

public record ItemListingDataDTO(
    String itemName,
    String memberName,
    String description,
    int price,
    List<Category> categories,
    AlbumDataDTO album
){
}
