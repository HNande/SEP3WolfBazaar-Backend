package org.example.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "item_listing")
public class ItemListing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long itemId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false)
  private String itemName;

  @Column(length = 670)
  private String description;


  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private LocalDate listDate;

  @OneToMany(mappedBy = "itemListing", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemPicture> pictures = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "item_listing_category",
      joinColumns = @JoinColumn(name = "item_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  protected ItemListing() {
  }

  public ItemListing(Member member, String itemName, String description, int price) {
    this.member = member;
    this.itemName = itemName;
    this.description = description;
    this.price = price;
  }

  @PrePersist
  void onCreate() {
    if (listDate == null) {
      listDate = LocalDate.now();
    }
  }

  public void addPicture(ItemPicture picture) {
    pictures.add(picture);
    picture.setItemListing(this);
  }

  public void removePicture(ItemPicture picture) {
    pictures.remove(picture);
    picture.setItemListing(null);
  }

  public void addCategory(Category category) {
    categories.add(category);
  }

  public void removeCategory(Category category) {
    categories.remove(category);
  }

  public Long getItemId() {
    return itemId;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public LocalDate getListDate() {
    return listDate;
  }

  public List<ItemPicture> getPictures() {
    return pictures;
  }

  public Set<Category> getCategories() {
    return categories;
  }
}