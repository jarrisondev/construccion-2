package com.club.api.clubapi.controller.requestDto;

import org.antlr.v4.runtime.misc.NotNull;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class GuestRequestDto extends PersonRequestDto {
  @NotNull
  private long partnerId;

}
