package org.example.application.controller;

import generated.ItemListing;
import jakarta.validation.Valid;
import org.example.application.repository.ItemListingJsonDTO;
import org.example.application.repository.ItemListingMapper;
import org.example.application.repository.ItemListingResponseDTO;
import org.example.application.service.ItemListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemListingController {

  private final ItemListingService itemListingService;

  @Autowired
  public ItemListingController(ItemListingService itemListingService) {
    this.itemListingService = itemListingService;
  }

  @GetMapping("")
  public List<ItemListingResponseDTO> getItemList() {
    return itemListingService.getItemList().getItemList().stream()
            .map(ItemListingResponseDTO::from)
            .toList();
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ItemListingResponseDTO> addItem(@Valid @ModelAttribute ItemListingJsonDTO form) {
    ItemListingResponseDTO saved = itemListingService.addItem(form);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(saved.id()).toUri();

    return ResponseEntity.created(location).body(saved);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemListingResponseDTO> getItemListingId(@PathVariable int id) {
    ItemListingResponseDTO item = itemListingService.getItemById(id);
    if (item == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(item);
  }
}