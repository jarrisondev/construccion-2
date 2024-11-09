package com.club.api.clubapi.dto;

import java.sql.Timestamp;

import com.club.api.clubapi.model.InvoiceStatus;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class InvoiceDto {
  private long id;
  private PersonDto personId;
  private PartnerDto partnerId;
  private Timestamp creationDate;
  private double amount;
  private InvoiceStatus status;

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
