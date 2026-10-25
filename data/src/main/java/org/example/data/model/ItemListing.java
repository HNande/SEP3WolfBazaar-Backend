package org.example.data.model;

import jakarta.persistence.*;
import org.example.data.model.Category;
import org.example.data.model.ItemPicture;
import org.example.data.model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_listing")
public class ItemListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "list_date", nullable = false)
    private LocalDate listDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "item_listing_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "itemListing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemPicture> pictures = new ArrayList<>();

    public ItemListing() {
    }

    public void addPicture(ItemPicture picture) {
        pictures.add(picture);
        picture.setItemListing(this);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public LocalDate getListDate() { return listDate; }
    public void setListDate(LocalDate listDate) { this.listDate = listDate; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

    public List<ItemPicture> getPictures() { return pictures; }
    public void setPictures(List<ItemPicture> pictures) { this.pictures = pictures; }
}