package org.example.application.service;

import generated.AddItemListingRequest;
import org.example.application.repository.ItemDAO;
import org.example.application.repository.ItemListingJsonDTO;
import org.example.application.repository.ItemListingMapper;
import org.example.application.repository.ItemListingResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemListingService {

  private final ItemDAO itemDAO;

  public ItemListingService(ItemDAO itemDAO) {
    this.itemDAO = itemDAO;
  }

  public ItemListingResponseDTO addItem(ItemListingJsonDTO dto) {
    AddItemListingRequest request = ItemListingMapper.toRequest(dto);
    return ItemListingMapper.toResponse(itemDAO.addItem(request));
  }

  public ItemListingResponseDTO getItemById(int id) {
    return itemDAO.getItemById(id)
            .map(ItemListingMapper::toResponse)
            .orElseThrow(() -> new NoSuchElementException("Item " + id + " not found"));
  }

  public List<ItemListingResponseDTO> getAllItems() {
    return itemDAO.getAllItems().stream()
            .map(ItemListingMapper::toResponse)
            .toList();
  }
}