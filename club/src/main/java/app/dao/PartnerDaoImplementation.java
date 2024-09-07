package app.dao;

import java.sql.PreparedStatement;

import app.config.MYSQLConnection;
import app.dao.interfaces.PartnerDao;
import app.dto.PartnerDto;
import app.helpers.Helper;
import app.model.Partner;

public class PartnerDaoImplementation implements PartnerDao {

	@Override
	public void createPartner(PartnerDto partnerDto) throws Exception {

		Partner partner = Helper.parse(partnerDto);
		String query = "INSERT INTO PARTNER(AMOUNT,TYPE,PERSONID) VALUES (?,?,?) ";
		PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
		preparedStatement.setDouble(1, partner.getAmount());
		preparedStatement.setString(2, partner.getType());
		preparedStatement.setLong(3, partner.getUserId().getPersonId().getCedula());
		preparedStatement.execute();
		preparedStatement.close();
	}

	@Override
	public void deletePartner(PartnerDto partnerDto) throws Exception {
		// Partner partner = Helper.parse(partnerDto);
		// String query = "DELETE FROM PARTNER WHERE CEDULA = ?";
		// PreparedStatement preparedStatement =
		// MYSQLConnection.getConnection().prepareStatement(query);
		// preparedStatement.setLong(1, partner.getCedula());
		// preparedStatement.execute();
		// preparedStatement.close();
	}

}
