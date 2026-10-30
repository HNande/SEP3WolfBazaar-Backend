package org.example.data.dto;

import java.time.LocalDate;

public record ItemSummaryDataDTO(
        int itemId,
        String itemName,
        String description,
        int price,
        LocalDate listDate
) {}
