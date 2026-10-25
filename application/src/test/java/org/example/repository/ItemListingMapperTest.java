package org.example.repository;

import generated.Category;
import org.junit.jupiter.api.Test;

import static org.example.application.repository.ItemListingMapper.CATEGORY_FROM_PROTO;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemListingMapperTest {
  @Test
  void everyProtoCategoryIsMapped() {
    for (Category c : Category.values()) {
      if (c == Category.UNRECOGNIZED) continue;
      assertTrue(CATEGORY_FROM_PROTO.containsKey(c), "Unmapped: " + c);
    }
  }
}
