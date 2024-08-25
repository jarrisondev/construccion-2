package app.service.interfaces;

import java.util.List;

import app.dto.UserDto;

public interface AdminService {
	public void createPartner(UserDto userDto) throws Exception;

	public List<UserDto> listPartners() throws Exception;

	// public void createVeterinarian(UserDto userDto) throws Exception;
}
