package org.example.data.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int messageId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sender_id", nullable = false)
  private Member sender;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "receiver_id", nullable = false)
  private Member receiver;

  // The listing the message is about ("discussed in")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "item_id", nullable = false)
  private ItemListing itemListing;

  @Column(nullable = false, length = 2000)
  private String text;

  @Column(nullable = false)
  private LocalDateTime time;

  protected Message() {
  }

  public Message(Member sender, Member receiver, ItemListing itemListing, String text) {
    this.sender = sender;
    this.receiver = receiver;
    this.itemListing = itemListing;
    this.text = text;
  }

  @PrePersist
  void onCreate() {
    if (time == null) {
      time = LocalDateTime.now();
    }
  }

  public int getMessageId() {
    return messageId;
  }

  public Member getSender() {
    return sender;
  }

  public Member getReceiver() {
    return receiver;
  }

  public ItemListing getItemListing() {
    return itemListing;
  }

  public String getText() {
    return text;
  }

  public LocalDateTime getTime() {
    return time;
  }
}