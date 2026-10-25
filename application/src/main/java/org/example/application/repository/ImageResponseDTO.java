package org.example.application.repository;

public record ImageResponseDTO(
    String name,
    ImageFileTypeDTO filetype,
    int width,
    int height,
    byte[] data
) {
}
