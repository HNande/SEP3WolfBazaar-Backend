package org.example.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_picture")
public class ItemPicture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pictureId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private ItemListing itemListing;

  @Column(nullable = false)
  private String fileName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ImageFileType fileType;

  @Column(nullable = false)
  private String filePath;

  protected ItemPicture() {
  }

  public ItemPicture(String fileName, ImageFileType fileType, String filePath) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.filePath = filePath;
  }

  public Long getPictureId() {
    return pictureId;
  }

  public ItemListing getItemListing() {
    return itemListing;
  }


  void setItemListing(ItemListing itemListing) {
    this.itemListing = itemListing;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public ImageFileType getFileType() {
    return fileType;
  }

  public void setFileType(ImageFileType fileType) {
    this.fileType = fileType;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}