package org.example.application.repository;

import org.example.application.model.ItemListing;

public record ItemResponseDTO(int id, String name, String description, int price) {

    public static ItemResponseDTO from(ItemListing item) {
        return new ItemResponseDTO(
                item.getItemListingId(),
                item.getItemListingName(),
                item.getItemListingDescription(),
                item.getItemListingPrice()
        );
    }
}