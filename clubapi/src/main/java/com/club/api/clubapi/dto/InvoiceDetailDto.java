package com.club.api.clubapi.dto;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class InvoiceDetailDto {
  private long id;
  private InvoiceDto invoiceId;
  private int item;
  private String description;
  private double amount;
}
