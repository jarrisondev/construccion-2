package app.controller;

public class PartnerController implements ControllerInterface {
	private static final String MENU = "Ingrese la opcion la accion que desea hacer \n 1. para crear factura. \n 2. para cerrar sesion";

	@Override
	public void session()  {
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

	private boolean menu(String option)  {
		switch (option) {
			case "1": {
				this.createInvoice();
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

	public PartnerController() {

	}

	private void createInvoice()  {
	}

}
