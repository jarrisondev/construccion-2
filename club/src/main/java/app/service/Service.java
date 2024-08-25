package app.service;

import java.sql.SQLException;
import java.util.List;

import app.controller.Utils;
import app.dao.PersonDaoImplementation;
import app.dao.UserDaoImplementation;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.helpers.Roles;
import app.service.interfaces.AdminService;

public class Service implements AdminService {
  private UserDao userDao;
  private PersonDao personDao;

  public static UserDto user;

  public Service() {
    this.userDao = new UserDaoImplementation();
    this.personDao = new PersonDaoImplementation();
  }

  @Override
  public void createPartner(UserDto userDto) throws Exception {
    this.createUser(userDto);
  }

  public void login(UserDto userDto) throws Exception {
    UserDto validateDto = userDao.findByUserName(userDto);
    if (validateDto == null) {
      throw new Exception("no existe usuario registrado");
    }
    if (!userDto.getPassword().equals(validateDto.getPassword())) {
      throw new Exception("usuario o contraseña incorrecto");
    }
    userDto.setRole(validateDto.getRole());
    user = validateDto;
  }

  private void createUser(UserDto userDto) throws Exception {
    this.createPerson(userDto.getPersonId());

    PersonDto personDto = personDao.findByCedula(userDto.getPersonId());
    userDto.setPersonId(personDto);
    if (this.userDao.existsByUserName(userDto)) {
      this.personDao.deletePerson(userDto.getPersonId());
      throw new Exception("ya existe un usuario con ese user name");
    }
    try {
      this.userDao.createUser(userDto);
    } catch (SQLException e) {
      this.personDao.deletePerson(userDto.getPersonId());
    }
  }

  private void createPerson(PersonDto personDto) throws Exception {
    if (this.personDao.existsByCedula(personDto)) {
      throw new Exception("ya existe una persona con ese documento");
    }
    this.personDao.createPerson(personDto);
  }

  @Override
  public List<UserDto> listPartners() throws Exception {
    try {
      List<UserDto> users = userDao.findUsersByRole(Roles.getPARTNER());
      for (UserDto userDto : users) {
        PersonDto personDto = personDao.findByCedula(userDto.getPersonId());
        userDto.setPersonId(personDto);
      }

      return users;

    } catch (Exception e) {
      Utils.log(e.getMessage());
      throw new RuntimeException("error al listar los socios");
    }
  }

}
