package org.example.application.repository;

public record ThumbnailResponseDTO(
    int itemId,
    ImageFileTypeJsonDTO filetype,
    int width,
    int height,
    byte[] data
) {
}
