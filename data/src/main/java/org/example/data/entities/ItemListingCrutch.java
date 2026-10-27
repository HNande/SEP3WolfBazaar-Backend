package org.example.data.entities;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// (Artem) A proxy class for access from DataItemListingService.
// I didn't want to change the constructor signature in ItemListing.
public class ItemListingCrutch extends ItemListing {

    private final ItemListing target;

    public ItemListingCrutch() {
        target = new ItemListing();
    }

    @Override
    public int getItemId() { return target.getItemId(); }

    @Override
    public void setItemId(int itemId) { target.setItemId(itemId); }

    @Override
    public Member getMember() { return target.getMember(); }

    @Override
    public void setMember(Member member) { target.setMember(member); }

    @Override
    public String getItemName() { return target.getItemName(); }

    @Override
    public void setItemName(String itemName) { target.setItemName(itemName); }

    @Override
    public String getDescription() { return target.getDescription(); }

    @Override
    public void setDescription(String description) { target.setDescription(description); }

    @Override
    public int getPrice() { return target.getPrice(); }

    @Override
    public void setPrice(int price) { target.setPrice(price); }

    @Override
    public LocalDate getListDate() { return target.getListDate(); }

    @Override
    public void setListDate(LocalDate listDate) { target.setListDate(listDate); }

    @Override
    public Set<Category> getCategories() { return target.getCategories(); }

    @Override
    public void setCategories(Set<Category> categories) { target.setCategories(categories); }

    @Override
    public List<ItemPicture> getPictures() { return target.getPictures(); }

    @Override
    public void setPictures(List<ItemPicture> pictures) { target.setPictures(pictures); }

    @Override
    public void addPicture(ItemPicture picture) { target.addPicture(picture); }

    @Override
    public void removePicture(ItemPicture picture) { target.removePicture(picture); }

    @Override
    public void addCategory(Category category) { target.addCategory(category); }
}