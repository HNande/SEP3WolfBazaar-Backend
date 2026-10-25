package org.example.application.repository;

import generated.categories;
import org.springframework.web.multipart.MultipartFile;

public record ItemListingJsonDTO(
     int id,
     String itemName,
     String memberName,
     String description,
     int price,
     MultipartFile[] album,
     java.util.List<categories> categories,
     int year,
     int month,
     int day
) {

}