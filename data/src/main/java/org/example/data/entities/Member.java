package org.example.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int memberId;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  protected Member(){

  }

  public Member(String fullName, String password)
  {
    this.fullName = fullName;
    this.password = password;
  }

  //(Artem) Fixed a bug in the DataItemListingService class without making extensive changes to it.
  public Member(String email, String defaultPassword, boolean shit) {
    this.email = email;
    password = defaultPassword;
  }

  public int getMemberId()
  {
    return memberId;
  }

  public String getFullName()
  {
    return fullName;
  }

  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
