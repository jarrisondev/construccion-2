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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoice")

public class Invoice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;
  @JoinColumn(name = "personid")
  @ManyToOne
  private Person personId;
  @JoinColumn(name = "partnerid")
  @ManyToOne
  private Partner partnerId;
  @Column(name = "creationdate")
  private Timestamp creationDate;
  @Column(name = "amount")
  private double amount;
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private InvoiceStatus status;
}
