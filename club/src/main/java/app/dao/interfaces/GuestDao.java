package app.dao.interfaces;

import app.dto.GuestDto;

public interface GuestDao {

	public void createGuest(GuestDto guestDto) throws Exception;

}
