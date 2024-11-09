package com.club.api.clubapi.service.serviceDto;

import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.UserDto;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class UserServiceDto extends UserDto {
  private PartnerDto partnerId;
  private GuestDto guestId;

}
