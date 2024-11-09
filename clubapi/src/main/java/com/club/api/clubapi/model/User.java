
package com.club.api.clubapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @JoinColumn(name = "personnid")
  @OneToOne
  private Person personId;
  @Column(name = "username")
  private String userName;
  @Column(name = "password")
  private String password;
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;
}

// public class User {
// private long id;
// private Person personId;
// private String username;
// private String password;
// private String role;

// }
