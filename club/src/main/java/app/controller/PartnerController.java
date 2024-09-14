package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.helpers.Roles;
import app.service.Service;
import app.service.interfaces.PartnerService;

public class PartnerController implements ControllerInterface {

	private PersonValidator personValidator;
	private UserValidator userValidator;
	private PartnerService service;
	private static final String MENU = "Ingrese la opcion la accion que desea hacer \n 1. Para crear invitados. \n 2. Para listar invitados \n 3. para cerrar sesion";

	public PartnerController() {
		this.personValidator = new PersonValidator();
		this.userValidator = new UserValidator();
		this.service = new Service();
	}

	@Override
	public void session(UserDto currentUserDto) {
		boolean session = true;
		while (session) {
			session = partnerSession(currentUserDto);
		}

	}

	private boolean partnerSession(UserDto currentUserDto) {
		try {
			Utils.log(MENU);
			String option = Utils.getReader().nextLine();
			return menu(option, currentUserDto);
		} catch (Exception e) {
			Utils.log(e.getMessage());
			return true;
		}
	}

	private boolean menu(String option, UserDto currentUserDto) throws Exception {
		switch (option) {
			case "1": {
				this.createGuest(currentUserDto);
				return true;
			}
			case "2": {
				this.listGuests();
				return true;
			}
			case "3": {
				Utils.log("se ha cerrado sesion");
				return false;
			}
			default: {
				Utils.log("opcion invalida");
				return true;
			}
		}

	}

	private void listGuests() throws Exception {
		this.service.listGuests().forEach(user -> {
			Utils.log(user.getPersonId().getName());
		});
	}

	private void createGuest(UserDto currentUserDto) throws Exception {

		Utils.log("ingrese el nombre del invitado");
		String name = Utils.getReader().nextLine();
		personValidator.validName(name);
		Utils.log("ingrese la cedula del invitado");
		long document = personValidator.validCedula(Utils.getReader().nextLine());
		Utils.log("ingrese el numero del invitado");
		long cellPhone = personValidator.validCellPhone(Utils.getReader().nextLine());
		Utils.log("ingrese el nombre de usuario del invitado");
		String userName = Utils.getReader().nextLine();
		userValidator.validUserName(userName);
		Utils.log("ingrese la contraseña del invitado");
		String password = Utils.getReader().nextLine();
		userValidator.validPassword(password);

		PersonDto personDto = new PersonDto();
		personDto.setName(name);
		personDto.setCedula(document);
		personDto.setCellPhone(cellPhone);

		UserDto userDto = new UserDto();
		userDto.setPersonId(personDto);
		userDto.setUsername(userName);
		userDto.setPassword(password);
		userDto.setRole(Roles.getGUEST());

		GuestDto guestDto = new GuestDto();
		guestDto.setUserId(userDto);

		PartnerDto partnerDto = new PartnerDto();
		partnerDto.setUserId(currentUserDto);

		PartnerDto resultPartnerDto = this.service.getPartner(partnerDto);
		if (resultPartnerDto == null) {
			throw new Exception("no se ha encontrado el socio");
		}
		guestDto.setPartnerId(resultPartnerDto);
		guestDto.setStatus(true);

		this.service.createGuest(guestDto);
		Utils.log("se ha creado el usuario exitosamente");
	}

}
