
package com.club.api.clubapi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.api.clubapi.dao.repositories.GuestRepository;
import com.club.api.clubapi.dto.GuestDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.Guest;
import com.club.api.clubapi.model.Partner;
import com.club.api.clubapi.model.User;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@NoArgsConstructor

public class GuestDao {

	@Autowired
	public GuestRepository guestRepository;

	public Optional<GuestDto> findById(Long id) throws Exception {
		Guest guest = guestRepository.findById(id).orElse(null);
		if (guest == null) {
			return Optional.empty();
		}
		return Optional.of(Helper.parse(guest));
	}

	public void createGuest(GuestDto guestDto) throws Exception {
		Guest guest = Helper.parse(guestDto);
		guestRepository.save(guest);
	}

	public List<GuestDto> findByPartnerId(PartnerDto partnerDto) throws Exception {
		Partner partner = Helper.parse(partnerDto);
		List<GuestDto> guestsDto = new ArrayList<>();
		List<Guest> guests = guestRepository.findByPartnerId(partner);
		for (Guest guest : guests) {
			GuestDto guestDto = Helper.parse(guest);
			guestsDto.add(guestDto);
		}
		return guestsDto;
	}

	@Transactional
	public void updateGuest(GuestDto guestDto) throws Exception {
		Optional<Guest> optionalGuest = guestRepository.findById(guestDto.getId());

		if (optionalGuest.isEmpty()) {
			throw new Exception("Invitado no encontrado.");
		}

		Guest guest = optionalGuest.get();

		guest.setStatus(guestDto.getStatus());
		guestRepository.save(guest);
	}

	public Optional<GuestDto> findByUserId(UserDto userDto) throws Exception {
		User user = Helper.parse(userDto);
		Guest guest = guestRepository.findByUserId(user);
		if (guest == null) {
			return Optional.empty();
		}
		return Optional.of(Helper.parse(guest));
	}
}
