package org.example.application.repository;

import generated.categories;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Set;

public record ItemListingJsonDTO(
    @NotBlank(message = "Item Name is required")
    @Size(min = ItemListingConstraints.ITEM_NAME_MIN, max = ItemListingConstraints.ITEM_NAME_MAX,
        message = "Item name must be between {min} and {max} characters")
    String itemName,

     @NotBlank(message = "Member name is required")
     @Size(min = ItemListingConstraints.MEMBER_NAME_MIN,max = ItemListingConstraints.MEMBER_NAME_MAX,
         message = "Member name must be between {min} and {max} characters")
     String memberName,

    @NotBlank(message = "Description is required")
    @Size(min = ItemListingConstraints.DESCRIPTION_MIN,max = ItemListingConstraints.DESCRIPTION_MAX,
        message = "Description must be between {min} and {max} characters")
     String description,

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    int price,
     @Valid AlbumJsonDTO album,
     //Set is used to remove duplicate categories just in case
    @NotEmpty(message = "At least one category is required")
     Set<@NotNull CategoryDTO> categories
) {

}