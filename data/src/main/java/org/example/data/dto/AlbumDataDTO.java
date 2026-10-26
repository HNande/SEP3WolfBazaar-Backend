package org.example.data.dto;

import java.util.List;

public record AlbumDataDTO(
    String title,
    List<ImageDataDTO> images
) {
}
