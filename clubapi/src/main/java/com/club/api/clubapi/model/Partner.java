package com.club.api.clubapi.model;

import java.sql.Timestamp;

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
@Table(name = "partner")

public class Partner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @JoinColumn(name = "userid")
  @OneToOne
  private User userId;
  @Column(name = "amount")
  private double amount;
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private SuscriptionType type;
  @Column(name = "creationdate")
  private Timestamp creationDate;
}
