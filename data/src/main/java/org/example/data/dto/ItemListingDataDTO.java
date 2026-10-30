package org.example.data.dto;

public record ItemListingDataDTO(
    String itemName,
    String description,
    int price,
    AlbumDataDTO album
){
}
