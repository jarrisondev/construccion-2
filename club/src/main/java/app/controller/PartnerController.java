package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.helpers.Roles;
import app.service.Service;
import app.service.interfaces.AdminService;

public class PartnerController implements ControllerInterface {

	private PersonValidator personValidator;
	private UserValidator userValidator;
	private AdminService service;
	private static final String MENU = "Ingrese la opcion la accion que desea hacer \n 1. Para crear invitados. \n 2. para cerrar sesion";

	public PartnerController() {
		this.personValidator = new PersonValidator();
		this.userValidator = new UserValidator();
		this.service = new Service();
	}

	@Override
	public void session() {
		boolean session = true;
		while (session) {
			session = partnerSession();
		}

	}

	private boolean partnerSession() {
		try {
			Utils.log(MENU);
			String option = Utils.getReader().nextLine();
			return menu(option);
		} catch (Exception e) {
			Utils.log(e.getMessage());
			return true;
		}
	}

	private boolean menu(String option) throws Exception {
		switch (option) {
			case "1": {
				this.createGuest();
				return true;
			}
			case "2": {
				Utils.log("se ha cerrado sesion");
				return false;
			}
			default: {
				Utils.log("opcion invalida");
				return true;
			}
		}

	}

	private void createGuest() throws Exception {
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

		// this.service.create(userDto);
		Utils.log("se ha creado el usuario exitosamente");
	}

}
