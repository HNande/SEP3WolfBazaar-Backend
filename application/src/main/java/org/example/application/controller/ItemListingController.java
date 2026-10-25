package org.example.application.controller;

import generated.Date;
import generated.ItemListing;
import org.example.application.repository.ItemListingJsonDTO;
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
  public ResponseEntity<ItemResponseDTO> addItem(@ModelAttribute ItemListingJsonDTO form) {
    ItemListing item =  ItemListing.newBuilder()
        .setId(form.getId())
        .setItemName(form.getItemName())
        .setMemberName(form.getMemberName())
        .setDescription(form.getDescription())
        .setPrice(form.getPrice())
        .setDate(Date.newBuilder()
            .setYear(form.getYear())
            .setMonth(form.getMonth())
            .setDay(form.getDay())
                .build())
        .addCategories(form.getCategories())
        //Bruh
        .setAlbum(form.getAlbum())
        .build();

    itemListingService.addItem(item, form.getAlbum());

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(item.getId()).toUri();

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