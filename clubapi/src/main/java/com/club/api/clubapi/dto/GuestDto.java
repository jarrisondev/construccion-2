package com.club.api.clubapi.dto;

import com.club.api.clubapi.model.GuestStatus;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class GuestDto {
  private long id;
  private UserDto userId;
  private PartnerDto partnerId;
  private GuestStatus status;
}
