package org.example.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String password;

  protected Member(){

  }

  public Member(String fullName, String password)
  {
    this.fullName = fullName;
    this.password = password;
  }

  public Long getMemberId()
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
}
