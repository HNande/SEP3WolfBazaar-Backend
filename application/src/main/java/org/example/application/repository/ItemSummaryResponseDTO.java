package org.example.application.repository;

public record ItemSummaryResponseDTO(
    int id,
    String itemName,
    String description,
    int price,
    int year,
    int month,
    int day
) {
}
