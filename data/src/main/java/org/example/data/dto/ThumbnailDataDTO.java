package org.example.data.dto;

import org.example.data.entities.ImageFileType;

public record ThumbnailDataDTO(
        int itemId,
        ImageFileType fileType,
        int width,
        int height,
        byte[] bytes
) {}
