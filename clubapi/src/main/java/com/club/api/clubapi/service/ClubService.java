
package com.club.api.clubapi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.api.clubapi.dao.GuestDao;
import com.club.api.clubapi.dao.InvoiceDao;
import com.club.api.clubapi.dao.PartnerDao;
import com.club.api.clubapi.dao.PersonDao;
import com.club.api.clubapi.dao.UserDao;
import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.InvoiceDetailDto;
import com.club.api.clubapi.dto.InvoiceDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.PersonDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.GuestStatus;
import com.club.api.clubapi.model.InvoiceStatus;
import com.club.api.clubapi.model.Role;
import com.club.api.clubapi.model.SuscriptionType;

@Service
public class ClubService {
  @Autowired
  UserDao userDao;
  @Autowired
  PersonDao personDao;
  @Autowired
  InvoiceDao invoiceDao;
  @Autowired
  PartnerDao partnerDao;
  @Autowired
  GuestDao guestDao;

  private UserDto user;
  private PartnerDto partner;
  private GuestDto guest;

  public void login(UserDto userDto) throws Exception {
    UserDto validateDto = userDao.findByUserName(userDto);
    if (validateDto == null) {
      throw new Exception("No existe usuario ingresado.");
    }
    if (!userDto.getPassword().equals(validateDto.getPassword())) {
      throw new Exception("Usuario o contraseña incorrecto");
    }
    userDto.setRole(validateDto.getRole());
    user = validateDto;
    if (user.getRole() == Role.PARTNER) {
      partner = this.getCurrentPartner();
    } else if (user.getRole() == Role.GUEST) {
      guest = this.getCurrentGuest();
    }
  }

  public void logout() {
    user = null;
    partner = null;
    guest = null;
    System.out.println("Se ha cerrado sesion \n");
  }

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

  public List<UserDto> listByRole(Role role) throws Exception {
    try {
      List<UserDto> users = userDao.findByRole(role);

      for (UserDto userDto : users) {
        PersonDto personDto = personDao.findByDocument(userDto.getPersonId());
        userDto.setPersonId(personDto);
      }

      return users;

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
    guestDto.setPartnerId(partner);
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

  public void createPartnerFromGuest(PartnerDto partnerDto) throws Exception {
    partnerDto.setUserId(user);
    user.setRole(Role.PARTNER);
    guest.setStatus(GuestStatus.INACTIVE);
    try {
      this.partnerDao.createPartner(partnerDto);
      this.updateUser(user);
      this.updateGuest(guest);
    } catch (SQLException e) {
      throw new Exception("Error creando Socio: " + e.getMessage());
    }
  }

  public List<InvoiceDto> getAllInvoices() throws Exception {
    try {
      return this.invoiceDao.findAll();
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de facturas: " + e);
    }
  }

  public List<InvoiceDto> getInvoicesByRole(Role role) throws Exception {
    try {
      return this.invoiceDao.findByRole(role);
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de facturas por rol: " + e);
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
      return this.partnerDao.findByUserId(user);
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos del Socio: " + e);
    }
  }

  public GuestDto getCurrentGuest() throws Exception {
    try {
      return this.guestDao.findByUserId(user);
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

  public List<InvoiceDto> getPendingInvoicesByCurrentPartnerId() throws Exception {
    try {
      List<InvoiceDto> invoices = this.invoiceDao.findByPartnerId(partner);
      List<InvoiceDto> pendingInvoices = invoices.stream()
          .filter(invoice -> invoice.getStatus() == InvoiceStatus.PENDING)
          .collect(Collectors.toList());
      return pendingInvoices;
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de facturas pendientes: " + e);
    }
  }

  public void createInvoice(InvoiceDto invoiceDto, InvoiceDetailDto details[]) throws Exception {
    try {
      PersonDto personDto = new PersonDto();
      personDto.setId(user.getPersonId().getId());
      invoiceDto.setPersonId(personDto);

      InvoiceDto newInvoice = this.invoiceDao.createInvoice(invoiceDto);
      for (InvoiceDetailDto detail : details) {
        detail.setInvoiceId(newInvoice);
        this.invoiceDao.createInvoiceDetail(detail);
      }

      System.out.println("Factura creada con éxito. \n");
    } catch (SQLException e) {
      throw new Exception("Error creando facturas: " + e);
    }
  }

  public double payPendingInvoices(double currentAmount) throws Exception {
    List<InvoiceDto> pendingInvoices = this.getPendingInvoicesByCurrentPartnerId();
    double newAmount = currentAmount;
    for (InvoiceDto invoice : pendingInvoices) {
      double newAmountToSet = newAmount - invoice.getAmount();
      if (newAmountToSet >= 0) {
        invoice.setStatus(InvoiceStatus.PAID);
        this.invoiceDao.updateInvoice(invoice);
        newAmount = newAmountToSet;
        System.out.println("Factura " + invoice.getId() + " pagada: -" + invoice.getAmount() + "\n");
      }
    }
    System.out.println("Nuevo saldo: " + newAmount + "\n");
    return newAmount;
  }

  public void updateGuestStatus(UserDto userDto) throws Exception {
    try {
      GuestDto guestDto = this.guestDao.findByUserId(userDto);
      if (guestDto == null) {
        throw new Exception("Invitado no encontrado.");
      }
      if (guestDto.getStatus() == GuestStatus.ACTIVE) {
        guestDto.setStatus(GuestStatus.INACTIVE);
      } else {
        guestDto.setStatus(GuestStatus.ACTIVE);
      }
      this.guestDao.updateGuest(guestDto);
    } catch (SQLException e) {
      throw new Exception("Error actualizando estado del Invitado: " + e);
    }
  }

  public List<GuestDto> getGuestsByCurrentPartner() throws Exception {
    try {
      return this.guestDao.findByPartnerId(partner);
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de invitados del socio: " + e);
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

  public List<InvoiceDto> getAllInvoicesByPartnerId() throws Exception {
    try {
      return this.invoiceDao.findByPartnerId(partner);
    } catch (SQLException e) {
      throw new Exception("Error obteniendo datos de facturas: " + e);
    }
  }
}
