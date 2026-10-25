package org.example.data.model;

import jakarta.persistence.*;
import org.example.data.model.ItemListing;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ItemListing> itemListings = new ArrayList<>();

    public Member() {
    }

    public Member(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<ItemListing> getItemListings() { return itemListings; }
    public void setItemListings(List<ItemListing> itemListings) { this.itemListings = itemListings; }
}