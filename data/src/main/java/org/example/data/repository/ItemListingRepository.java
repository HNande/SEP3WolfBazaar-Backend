package org.example.data.repository;

import org.example.data.entities.ItemListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemListingRepository extends JpaRepository<ItemListing, Integer> {
}
