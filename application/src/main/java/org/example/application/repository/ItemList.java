package org.example.application.repository;

import generated.ItemListing;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private List<ItemListing> itemListing;

    public List<ItemListing> getItemList() {
        if (itemListing == null) {
            itemListing = new ArrayList<>();
        }
        return itemListing;
    }
}