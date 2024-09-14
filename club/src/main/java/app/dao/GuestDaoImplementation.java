package app.dao;

import java.sql.PreparedStatement;

import app.config.MYSQLConnection;
import app.dao.interfaces.GuestDao;
import app.dto.GuestDto;
import app.helpers.Helper;
import app.model.Guest;

public class GuestDaoImplementation implements GuestDao {

	@Override
	public void createGuest(GuestDto guestDto) throws Exception {

		Guest guest = Helper.parse(guestDto);
		String query = "INSERT INTO GUEST(STATUS,PERSONID) VALUES (?,?) ";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setBoolean(1, guest.getStatus());
		preparedStatement.setLong(2, guest.getUserId().getPersonId().getCedula());
		preparedStatement.execute();
		preparedStatement.close();
	}

}
