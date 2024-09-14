package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import app.config.MYSQLConnection;
import app.dao.interfaces.GuestDao;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Guest;

public class GuestDaoImplementation implements GuestDao {

	@Override
	public void createGuest(GuestDto guestDto) throws Exception {

		Guest guest = Helper.parse(guestDto);
		String query = "INSERT INTO GUEST(STATUS,PERSONID, PARTNERID) VALUES (?,?,?)";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setBoolean(1, guest.getStatus());
		preparedStatement.setLong(2, guest.getUserId().getPersonId().getCedula());
		preparedStatement.setLong(3, guest.getPartnerId().getUserId().getPersonId().getCedula());
		preparedStatement.execute();
		preparedStatement.close();
	}

	@Override
	public void changeStatus(GuestDto guestDto) throws Exception {
		String query = "UPDATE GUEST SET STATUS = ? WHERE PERSONID = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setBoolean(1, !guestDto.getStatus());
		preparedStatement.setLong(2, guestDto.getUserId().getPersonId().getCedula());
		preparedStatement.execute();
		preparedStatement.close();
	}

	@Override
	public GuestDto getGuest(GuestDto guestDto) throws Exception {
		String query = "SELECT * FROM GUEST WHERE PERSONID = ?";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setLong(1, guestDto.getUserId().getPersonId().getCedula());

		ResultSet resulSet = preparedStatement.executeQuery();
		if (resulSet.next()) {
			GuestDto guestResult = new GuestDto();
			guestResult.setStatus(resulSet.getBoolean("STATUS"));
			guestResult.setId(resulSet.getLong("ID"));

			UserDto userDto = new UserDto();
			PersonDto personDto = new PersonDto();
			personDto.setCedula(resulSet.getLong("PERSONID"));
			userDto.setPersonId(personDto);
			guestResult.setUserId(userDto);

			PartnerDto partnerDto = new PartnerDto();
			UserDto userDtoPartner = new UserDto();
			PersonDto personDtoPartner = new PersonDto();
			personDtoPartner.setCedula(resulSet.getLong("PARTNERID"));
			userDtoPartner.setPersonId(personDtoPartner);
			partnerDto.setUserId(userDtoPartner);
			guestResult.setPartnerId(partnerDto);

			resulSet.close();
			preparedStatement.close();

			return guestResult;
		}

		resulSet.close();
		preparedStatement.close();

		return null;
	}

}
