package org.example.application.repository;

import java.util.List;

public record AlbumResponseDTO(
    String title,
    List<ImageResponseDTO> images
) {
}
