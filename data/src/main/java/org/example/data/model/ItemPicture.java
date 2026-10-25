package org.example.data.model;

import jakarta.persistence.*;
import org.example.data.model.ItemListing;

@Entity
@Table(name = "item_picture")
public class ItemPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemListing itemListing;

    public ItemPicture() {}

    public ItemPicture(String fileName, String filePath)
        {
        this.fileName = fileName;
        this.filePath = filePath;
        }

    public int getPictureId() { return pictureId; }
    public void setPictureId(int pictureId) { this.pictureId = pictureId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public ItemListing getItemListing() { return itemListing; }
    public void setItemListing(ItemListing itemListing) { this.itemListing = itemListing; }

}
