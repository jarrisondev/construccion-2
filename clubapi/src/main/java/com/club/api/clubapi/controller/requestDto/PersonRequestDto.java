package com.club.api.clubapi.controller.requestDto;

import org.antlr.v4.runtime.misc.NotNull;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class PersonRequestDto {
  private long id;
  @NotNull
  private String name;
  @NotNull
  private long document;
  @NotNull
  private long cellPhone;
  @NotNull
  private String username;
  @NotNull
  private String password;

}
