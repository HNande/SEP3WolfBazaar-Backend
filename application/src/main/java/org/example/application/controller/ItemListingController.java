package org.example.application.controller;

import org.example.application.model.ItemListing;
import org.example.application.repository.ItemFormDTO;
import org.example.application.repository.ItemResponseDTO;
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
  public List<ItemResponseDTO> getItemList() {
    return itemListingService.getItemList().getItemList().stream()
            .map(ItemResponseDTO::from)
            .toList();
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ItemResponseDTO> addItem(@ModelAttribute ItemFormDTO form) {
    ItemListing item = new ItemListing(0);
    item.setItemListingName(form.getName());
    item.setItemListingDescription(form.getDescription());
    item.setItemListingPrice(form.getPrice());

    itemListingService.addItem(item, form.getPhotos());

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(item.getItemListingId()).toUri();

    return ResponseEntity.created(location).body(ItemResponseDTO.from(item));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> getItemListingId(@PathVariable int id) {
    ItemListing item = itemListingService.getItemById(id);
    if (item == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ItemResponseDTO.from(item));
  }
}