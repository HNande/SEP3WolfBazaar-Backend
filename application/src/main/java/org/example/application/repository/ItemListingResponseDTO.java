package org.example.application.repository;

import java.util.Set;

public record ItemListingResponseDTO(
    int id,
    String itemName,
    String memberName,
    String description,
    int price,
    Set<CategoryJsonDTO> categories,
    AlbumResponseDTO album
    ){
}