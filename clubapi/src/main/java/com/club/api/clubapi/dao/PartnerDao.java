
package com.club.api.clubapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.api.clubapi.dao.repositories.PartnerRepository;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.dto.UserDto;
import com.club.api.clubapi.model.Partner;
import com.club.api.clubapi.model.SuscriptionType;
import com.club.api.clubapi.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@NoArgsConstructor

public class PartnerDao {
	@Autowired
	public PartnerRepository partnerRepository;
	@Autowired
	private InvoiceDao invoiceDao;

	public void createPartner(PartnerDto partnerDto) throws Exception {
		Partner partner = Helper.parse(partnerDto);
		partnerRepository.save(partner);
	}

	public PartnerDto findByUserId(UserDto userDto) throws Exception {
		User user = Helper.parse(userDto);
		Partner partner = partnerRepository.findByUserId(user);
		if (partner == null) {
			return null;
		}
		return Helper.parse(partner);
	}

	public List<PartnerDto> findByType(SuscriptionType type) throws Exception {
		List<PartnerDto> partnersDto = new ArrayList<>();
		List<Partner> partners = partnerRepository.findByType(type);

		for (Partner partner : partners) {
			PartnerDto partnerDto = Helper.parse(partner);
			double totalInvoicesAmountPaid = invoiceDao.getTotalInvoicesAmountPaid(partnerDto);
			partnerDto.setTotalInvoicesAmountPaid(totalInvoicesAmountPaid);
			partnersDto.add(partnerDto);
		}

		return partnersDto;
	}
}
