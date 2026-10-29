package org.example.data.dto;

public record ItemListingResponseDTO(
    int id,
    String itemName,
    String description,
    int price,
    AlbumDataDTO album,
    int year,
    int month,
    int day

) {

}
