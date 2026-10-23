package controller;

import model.ItemListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.ItemList;
import service.ItemListingService;

import java.net.URI;

@RestController
@RequestMapping("/api/item")
public class ItemListingController {
    private ItemListingService itemListingService;

    @Autowired
    public ItemListingController(ItemListingService itemListingService)
    {
        this.itemListingService = itemListingService;
    }

    @GetMapping("")
    public ItemList getItemList()
    {
        return itemListingService.getItemList();
    }

    @PostMapping("")
    public ResponseEntity<Object> addItem(@RequestBody ItemListing item)
    {
       itemListingService.addItem(item);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getItemListingId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ItemListing getItemListingId(@PathVariable int id) {
        return itemListingService.getItemById(id);
    }
}
