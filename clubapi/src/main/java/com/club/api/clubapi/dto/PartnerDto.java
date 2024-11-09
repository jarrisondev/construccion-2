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

  @Override
  public String toString() {
    return """
        -----------------------------------------------
        * ID Socio: """ + id + "\n"
        + "* ID Usuario: " + userId.getId() + "\n"
        + "* Fecha de afiliación: " + creationDate.toString() + "\n"
        + "* Fondos Disponibles: " + amount + "\n"
        + "* Tipo de suscripción: " + type.toString() + "\n"
        + "----------------------------------------------- \n";
  }

}
