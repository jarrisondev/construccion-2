package com.club.api.clubapi.dto;

import java.sql.Timestamp;

import com.club.api.clubapi.model.InvoiceStatus;

public class InvoiceDto {
  private long id;
  private PersonDto personId;
  private PartnerDto partnerId;
  private Timestamp creationDate;
  private double amount;
  private InvoiceStatus status;

  public InvoiceDto() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public PersonDto getPersonId() {
    return personId;
  }

  public void setPersonId(PersonDto personId) {
    this.personId = personId;
  }

  public PartnerDto getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(PartnerDto partnerId) {
    this.partnerId = partnerId;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public InvoiceStatus getStatus() {
    return status;
  }

  public void setStatus(InvoiceStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return """
        -----------------------------------------------
        * ID: """ + id + "\n"
        + "* ID Consumidor: " + personId.getId() + "\n"
        + "* ID Socio: " + partnerId.getId() + "\n"
        + "* Fecha de generación: " + creationDate.toString() + "\n"
        + "* Valor total: " + amount + "\n"
        + "* Estado: " + status.toString() + "\n"
        + "----------------------------------------------- \n";
  }
}
