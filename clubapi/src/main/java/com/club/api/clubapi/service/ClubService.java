
package com.club.api.clubapi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.api.clubapi.dao.GuestDao;
import com.club.api.clubapi.dao.PartnerDao;
import com.club.api.clubapi.dao.PersonDao;
import com.club.api.clubapi.dao.UserDao;
import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.GuestStatus;
import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.model.SuscriptionType;
import com.club.api.clubapi.service.serviceDto.UserServiceDto;

@Service
public class ClubService {
  @Autowired
  UserDao userDao;
  @Autowired
  PersonDao personDao;
  @Autowired
  PartnerDao partnerDao;
  @Autowired
  GuestDao guestDao;

  private UserDto user;

  public void createPerson(PersonDto personDto) throws Exception {
    try {
      if (this.personDao.existsByDocument(personDto)) {
        throw new Exception("Ya existe una persona con ese documento");
      }
      this.personDao.createPerson(personDto);
    } catch (SQLException e) {
      throw new Exception("Error creando Persona: " + e);
    }
  }

  public void createUser(UserDto userDto) throws Exception {
    this.createPerson(userDto.getPersonId());
    PersonDto personDto = personDao.findByDocument(userDto.getPersonId());
    userDto.setPersonId(personDto);
    if (userDao.existsByUserName(userDto)) {
      this.personDao.deletePerson(userDto.getPersonId().getId());
      throw new Exception("Ya existe un usuario con ese Nombre de Usuario");
    }
    try {
      userDao.createUser(userDto);
    } catch (SQLException e) {
      this.personDao.deletePerson(userDto.getPersonId().getId());
      throw new Exception("Error creando Usuario: " + e.getMessage());
    }
  }

  public List<UserServiceDto> listByRole(Role role) throws Exception {
    try {
      List<UserServiceDto> usersFinal = new ArrayList<>();

      List<UserDto> users = userDao.findByRole(role);

      for (UserDto userDto : users) {

        UserServiceDto userServiceDto = new UserServiceDto();

        PersonDto personDto = personDao.findByDocument(userDto.getPersonId());
        userServiceDto.setPersonId(personDto);

        Optional<PartnerDto> partnerDto = partnerDao.findByUserId(userDto);
        Optional<GuestDto> guestDto = guestDao.findByUserId(userDto);

        if (partnerDto.isPresent()) {
          userServiceDto.setPartnerId(partnerDto.get());
        }
        if (guestDto.isPresent()) {
          userServiceDto.setGuestId(guestDto.get());
        }

        userServiceDto.setId(userDto.getId());
        userServiceDto.setUsername(userDto.getUsername());
        userServiceDto.setRole(userDto.getRole());

        usersFinal.add(userServiceDto);
      }

      return usersFinal;

    } catch (Exception e) {
      throw new RuntimeException("error al listar los usuarios por rol: " + e);
    }
  }

  public void createPartner(PartnerDto partnerDto) throws Exception {
    this.createUser(partnerDto.getUserId());
    UserDto userDto = userDao.findByUserName(partnerDto.getUserId());
    partnerDto.setUserId(userDto);
    try {
      this.partnerDao.createPartner(partnerDto);
      System.out.println("Se ha creado el socio correctamente");
    } catch (SQLException e) {
      this.personDao.deletePerson(userDto.getPersonId().getId());
      throw new Exception("Error creando Socio: " + e.getMessage());
    }

  }

  public void deletePerson(long id) throws Exception {
    try {
      this.personDao.deletePerson(id);

    } catch (SQLException e) {
      throw new Exception("Error eliminando Socio: " + e);
    }
  }

  public void createGuest(GuestDto guestDto) throws Exception {
    this.createUser(guestDto.getUserId());
    UserDto userDto = userDao.findByUserName(guestDto.getUserId());
    guestDto.setUserId(userDto);
    try {
      this.guestDao.createGuest(guestDto);
      System.out.println("Se ha creado el invitado correctamente");
    } catch (SQLException e) {
      this.personDao.deletePerson(userDto.getPersonId().getId());
      throw new Exception("Error creando Invitado: " + e);
    }
  }

  public List<PartnerDto> getPartnersByType(SuscriptionType type) throws Exception {
    try {
      return this.partnerDao.findByType(type);
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de socio por tipo de suscripción: " + e);
    }
  }

  public PartnerDto getCurrentPartner() throws Exception {
    try {
      Optional<PartnerDto> partnerDto = this.partnerDao.findByUserId(user);
      if (!partnerDto.isPresent()) {
        throw new Exception("Socio no encontrado.");
      }
      return partnerDto.get();
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos del Socio: " + e);
    }
  }

  public void requestVIPUpgrade(long id) throws Exception {
    try {
      Optional<PartnerDto> partnerDto = this.partnerDao.findById(id);
      if (!partnerDto.isPresent()) {
        throw new Exception("Socio no encontrado.");
      }
      partnerDto.get().setType(SuscriptionType.PENDING_VIP);
      this.partnerDao.updatePartner(partnerDto.get());
    } catch (SQLException e) {
      throw new Exception("Error solicitando actualización a VIP: " + e);
    }
  }

  public List<Long> upgradePartners() throws Exception {
    List<PartnerDto> partnersDtoPending = this.getPartnersByType(SuscriptionType.PENDING_VIP);
    List<PartnerDto> partnersDtoVIP = this.getPartnersByType(SuscriptionType.VIP);

    int maxVIP = 5;
    int currentVip = partnersDtoVIP.size();
    int currentPending = partnersDtoPending.size();
    int available = maxVIP - currentVip;
    List<Long> result = new ArrayList<>();

    if (currentPending < 1) {
      throw new Exception("No hay solicitudes pendientes.");
    }
    if (available <= 0) {
      throw new Exception("Se ha superado el límite máximo de clientes VIP");
    }

    partnersDtoPending.sort(Comparator.comparingDouble(PartnerDto::getTotalInvoicesAmountPaid).reversed()
        .thenComparingDouble(PartnerDto::getAmount).reversed()
        .thenComparing(PartnerDto::getCreationDate));

    for (int i = 0; i < available && i < currentPending; i++) {
      PartnerDto selectedPartner = partnersDtoPending.get(i);
      result.add(selectedPartner.getId());

      selectedPartner.setType(SuscriptionType.VIP);
      this.updatePartner(selectedPartner);
    }

    return result;
  }

  public GuestDto getCurrentGuest() throws Exception {
    try {
      Optional<GuestDto> guestDto = this.guestDao.findByUserId(user);
      if (!guestDto.isPresent()) {
        throw new Exception("Invitado no encontrado.");
      }
      return guestDto.get();

    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos del Invitado: " + e);
    }
  }

  public void deleteCurrentUser() throws Exception {
    try {
      this.personDao.deletePerson(user.getPersonId().getId());
    } catch (SQLException e) {
      throw new Exception("Error eliminando datos del socio: " + e);
    }
  }

  public void activateGuestByPartner(long partnerId, long guestId) throws Exception {
    try {
      Optional<PartnerDto> partner = partnerDao.findById(partnerId);
      if (!partner.isPresent()) {
        throw new Exception("Socio no encontrado.");
      }
      List<GuestDto> guests = this.guestDao.findByPartnerId(partner.get());
      int currentActive = 0;

      for (GuestDto guest : guests) {
        if (guest.getStatus() == GuestStatus.ACTIVE) {
          currentActive++;
        }
      }

      if (partner.get().getType() != SuscriptionType.VIP && currentActive >= 3) {
        throw new Exception("Excede límite de invitados activos permitido \n");
      }

      Optional<GuestDto> guest = this.guestDao.findById(guestId);
      if (!guest.isPresent()) {
        throw new Exception("Invitado no encontrado.");
      }
      guest.get().setStatus(GuestStatus.ACTIVE);
      this.guestDao.updateGuest(guest.get());
    } catch (SQLException e) {
      throw new Exception("Error activando invitado: " + e);
    }
  }

  public void deactivateGuestByPartner(long partnerId, long guestId) throws Exception {
    try {
      Optional<PartnerDto> partner = partnerDao.findById(partnerId);
      if (!partner.isPresent()) {
        throw new Exception("Socio no encontrado.");
      }

      Optional<GuestDto> guest = this.guestDao.findById(guestId);
      if (!guest.isPresent()) {
        throw new Exception("Invitado no encontrado.");
      }
      guest.get().setStatus(GuestStatus.INACTIVE);
      this.guestDao.updateGuest(guest.get());
    } catch (SQLException e) {
      throw new Exception("Error desactivando el invitado: " + e);
    }

  }

  public List<GuestDto> getGuestsByCurrentPartner(long id) throws Exception {

    try {
      Optional<PartnerDto> partner = partnerDao.findById(id);

      if (!partner.isPresent()) {
        throw new Exception("Socio no encontrado.");
      }
      return this.guestDao.findByPartnerId(partner.get());
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de invitados del socio: " + e);
    }
  }

  public void updatePartner(PartnerDto partnerDto) throws Exception {
    try {
      this.partnerDao.updatePartner(partnerDto);
    } catch (SQLException e) {
      throw new Exception("Error actualizando datos del socio: " + e);
    }
  }

  public void updateGuest(GuestDto guestDto) throws Exception {
    try {
      this.guestDao.updateGuest(guestDto);
    } catch (SQLException e) {
      throw new Exception("Error actualizando datos del Invitado: " + e);
    }
  }

  public void updateUser(UserDto userDto) throws Exception {
    try {
      userDao.updateUser(userDto);
    } catch (SQLException e) {
      throw new Exception("Error actualizando datos del usuario: " + e);
    }
  }

}
