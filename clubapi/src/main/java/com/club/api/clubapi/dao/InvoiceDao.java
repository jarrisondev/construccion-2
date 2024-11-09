
package com.club.api.clubapi.dao;

import com.club.api.clubapi.dao.repositories.InvoiceDetailRepository;
import com.club.api.clubapi.dao.repositories.InvoiceRepository;
import com.club.api.clubapi.dto.InvoiceDetailDto;
import com.club.api.clubapi.dto.InvoiceDto;
import com.club.api.clubapi.dto.PartnerDto;
import com.club.api.clubapi.model.Invoice;
import com.club.api.clubapi.model.InvoiceDetail;
import com.club.api.clubapi.model.InvoiceStatus;
import com.club.api.clubapi.model.Partner;
import com.club.api.clubapi.model.Role;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@NoArgsConstructor

public class InvoiceDao {

  @Autowired
  public InvoiceRepository invoiceRepository;
  @Autowired
  public InvoiceDetailRepository invoiceDetailRepository;

  public List<InvoiceDto> findAll() throws Exception {
    List<InvoiceDto> invoicesDto = new ArrayList<>();
    List<Invoice> invoices = invoiceRepository.findAll();
    for (Invoice invoice : invoices) {
      InvoiceDto invoiceDto = Helper.parse(invoice);
      invoicesDto.add(invoiceDto);
    }
    return invoicesDto;
  }

  public List<InvoiceDto> findByRole(Role role) throws Exception {
    List<InvoiceDto> invoicesDto = new ArrayList<>();
    List<Invoice> invoices = invoiceRepository.findByUserRole(role);
    for (Invoice invoice : invoices) {
      InvoiceDto invoiceDto = Helper.parse(invoice);
      invoicesDto.add(invoiceDto);
    }
    return invoicesDto;
  }

  public List<InvoiceDto> findByPartnerId(PartnerDto partnerDto) throws Exception {
    List<InvoiceDto> invoicesDto = new ArrayList<>();
    Partner partner = Helper.parse(partnerDto);
    List<Invoice> invoices = invoiceRepository.findByPartnerId(partner);
    for (Invoice invoice : invoices) {
      InvoiceDto invoiceDto = Helper.parse(invoice);
      invoicesDto.add(invoiceDto);
    }
    return invoicesDto;
  }

  public InvoiceDto createInvoice(InvoiceDto invoiceDto) throws Exception {
    Invoice invoice = Helper.parse(invoiceDto);
    Invoice newInvoice = invoiceRepository.save(invoice);
    return Helper.parse(newInvoice);
  }

  public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws Exception {
    InvoiceDetail invoiceDetail = Helper.parse(invoiceDetailDto);
    invoiceDetailRepository.save(invoiceDetail);
  }

  @Transactional
  public void updateInvoice(InvoiceDto invoiceDto) throws Exception {

    Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceDto.getId());
    if (optionalInvoice.isEmpty()) {
      throw new Exception("Factura no encontrada.");
    }
    Invoice invoice = optionalInvoice.get();
    invoice.setAmount(invoiceDto.getAmount());
    invoice.setStatus(invoiceDto.getStatus());
    invoiceRepository.save(invoice);
  }

  public double getTotalInvoicesAmountPaid(PartnerDto partnerDto) throws Exception {
    List<InvoiceDto> partnerInvoices = this.findByPartnerId(partnerDto);
    double total = 0;
    for (InvoiceDto invoice : partnerInvoices) {
      if (invoice.getStatus() == InvoiceStatus.PAID) {
        total += invoice.getAmount();
      }
    }
    return total;
  }
}
