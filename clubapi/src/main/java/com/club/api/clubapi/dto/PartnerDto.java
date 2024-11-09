package com.club.api.clubapi.dto;

import java.sql.Timestamp;

import com.club.api.clubapi.model.SuscriptionType;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class PartnerDto {
  private long id;
  private UserDto userId;
  private double amount;
  private SuscriptionType type;
  private Timestamp creationDate;
  private double totalInvoicesAmountPaid;
}
