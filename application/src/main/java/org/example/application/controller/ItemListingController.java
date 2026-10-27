package org.example.application.controller;

import jakarta.validation.Valid;
import org.example.application.repository.ItemListingJsonDTO;
import org.example.application.repository.ItemListingResponseDTO;
import org.example.application.service.ItemListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/item")
public class ItemListingController {

  private static final Logger LOG = LoggerFactory.getLogger(ItemListingController.class);

  private final ItemListingService itemListingService;

  @Autowired
  public ItemListingController(ItemListingService itemListingService) {
    this.itemListingService = itemListingService;
  }

  @GetMapping("")
  public ResponseEntity<List<ItemListingResponseDTO>> getItemList() {
    try {
      return ResponseEntity.ok(itemListingService.getAllItems());
    } catch (RuntimeException e) {
      LOG.error("Could not load item list", e);
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ItemListingResponseDTO> addItem(@Valid @ModelAttribute ItemListingJsonDTO form) {
    try {
      ItemListingResponseDTO saved = itemListingService.addItem(form);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}").buildAndExpand(saved.id()).toUri();

      return ResponseEntity.created(location).body(saved);

    } catch (IllegalArgumentException e) {
      LOG.warn("Invalid item: {}", e.getMessage());
      return ResponseEntity.badRequest().build();

    } catch (RuntimeException e) {
      LOG.error("Could not add item", e);
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemListingResponseDTO> getItemListingId(@PathVariable int id) {
    try {
      return ResponseEntity.ok(itemListingService.getItemById(id));

    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();

    } catch (RuntimeException e) {
      LOG.error("Could not load item {}", id, e);
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }
  }
}