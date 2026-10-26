package org.example.data.dto;

public record ImageDataDTO(
    String name,
    String filetype,
    int width,
    int height,
    byte[] data
) {
}
