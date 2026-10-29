package org.example.data.repository;

import org.example.data.entities.ItemListing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemListingRepository extends JpaRepository<ItemListing, Integer> {

  List<ItemListing> findByItemIdGreaterThanOrderByItemIdAsc(int afterId, Pageable pageable);
}
