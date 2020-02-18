package com.spbootlogistic.bill.service;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.repository.BillRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class BillService implements IBillService {

    @Autowired
    BillRepository billRepository;

    private static final Logger LOGGER = LogManager.getLogger(BillService.class);

    public Iterable<Bill> findAll() {
        return billRepository.findAll();
    }

    public Bill findById(long id) throws BillNotFoundException {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Bill Not Found with Id " + id);
                    return new BillNotFoundException("Item Not Found with Id " + id);
                });
        LOGGER.info("Bill Found with Id " + id);
        return bill;
    }

    public Bill save(Bill bill) throws InvalidBillException {
        if (isNull(bill)) {
            LOGGER.error("Bill Not Valid");
            throw new InvalidBillException("The Bill send is invalid");
        }
        LOGGER.info("Bill " + bill.getId() + " was saved");
        return billRepository.save(bill);
    }

    public Bill update(long id, Bill source) throws BillNotFoundException {
        Bill destination = billRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Bill Not Found with Id " + id);
                    return new BillNotFoundException("Item Not Found with Id " + id);
                });
        assignFields(destination, source);
        source.setId(destination.getId());
        LOGGER.info("Bill with id " + id + " was updated.");
        return billRepository.save(destination);
    }

    public void delete(long id) throws BillNotFoundException {
        if (!billRepository.findById(id).isPresent()) {
            LOGGER.error("Bill Not Found");
            throw new BillNotFoundException("Item Not Found with Id " + id);
        }
        LOGGER.info("Bill with Id " + id + " was deleted");
        billRepository.deleteById(id);
    }

    private void assignFields(Bill destination, Bill source) {
        destination.setCfdiUse(source.getCfdiUse());
        destination.setDate(source.getDate());
        destination.setFolioFiscal(source.getFolioFiscal());
        destination.setPdfURL(source.getPdfURL());
        destination.setReceiverName(source.getReceiverName());
        destination.setReceiverRFC(source.getReceiverRFC());
        destination.setTransmitterName(source.getTransmitterName());
        destination.setTransmitterRFC(source.getTransmitterRFC());
        destination.setTotal(source.getTotal());
        destination.setXmlURL(source.getXmlURL());
    }
}
