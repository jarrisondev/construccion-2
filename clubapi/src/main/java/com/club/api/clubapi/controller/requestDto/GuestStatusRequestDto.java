package com.club.api.clubapi.controller.requestDto;

import org.antlr.v4.runtime.misc.NotNull;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class GuestStatusRequestDto {
  @NotNull
  private long partnerId;
  @NotNull
  private long guestId;
}
