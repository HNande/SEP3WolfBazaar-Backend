package org.example.data.service;

import org.example.data.dao.ItemListingDAO;
import org.example.data.dto.ItemListingDataDTO;
import org.example.data.dto.ItemListingResponseDTO;
import org.example.data.entities.*;
import org.springframework.stereotype.Service;
import java.util.Optional;


//This class just checks for business rules, so whether or not there is a category or image included

@Service
public class DataItemListingService {

    private final ItemListingDAO itemListingDAO;

    public DataItemListingService(ItemListingDAO itemListingDAO) {
        this.itemListingDAO = itemListingDAO;
    }

    public ItemListingResponseDTO addItem(ItemListingDataDTO dto) {
        if (dto.categories().isEmpty()) {
            throw new IllegalArgumentException("A listing needs at least one category");
        }
        if (dto.album() == null || dto.album().images().isEmpty()) {
            throw new IllegalArgumentException("A listing needs at least one picture");
        }
        return itemListingDAO.addNewItemListing(dto);
    }

    public Optional<ItemListingResponseDTO> getItemById(int id) {
        return itemListingDAO.getItemById(id);
    }

    public ItemListingDAO.ItemListingPage getItems(int pageSize, String pageToken) {
        return itemListingDAO.getItems(pageSize, pageToken);
    }
}