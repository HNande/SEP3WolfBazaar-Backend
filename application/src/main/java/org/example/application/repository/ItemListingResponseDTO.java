package org.example.application.repository;

public record ItemListingResponseDTO(
    int id,
    String itemName,
    String description,
    int price,
    AlbumResponseDTO album,
    int year,
    int month,
    int day
    ){
}