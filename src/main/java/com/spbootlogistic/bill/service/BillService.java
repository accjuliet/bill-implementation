package com.spbootlogistic.bill.service;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.repository.BillRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class BillService implements IBillService{

    @Autowired
    BillRepository billRepository;

    private static final Logger LOGGER = LogManager.getLogger(BillService.class);

    public Iterable<Bill> findAll(){
        return billRepository.findAll();
    }

    public Bill findById(long id) throws BillNotFoundException {
        Bill bill = billRepository.findById(id).get();
        if(isNull(bill)){
            LOGGER.error("Bill Not Found with Id " + id);
            throw new BillNotFoundException("Item Not Found with Id " + id);
        }
        LOGGER.info("Bill Found with Id " + id);
        return bill;
    }

    public Bill save(Bill bill) throws InvalidBillException {
        if (isNull(bill)){
            LOGGER.error("Bill Not Valid");
            throw new InvalidBillException("The Bill send is invalid");
        }
        LOGGER.info("Bill " + bill.getId() + " was saved");
        return billRepository.save(bill);
    }

    public Bill update(long id, Bill source) throws InvalidBillException, BillNotFoundException {
        Bill target = billRepository.findById(id).get();
        if(isNull(target)){
            LOGGER.error("Bill Not Found");
            throw new BillNotFoundException("Bill Not Found with Id " + id);
        } else if(isNull(target)) {
            LOGGER.error("Invalid Item");
            throw new InvalidBillException("The Bill send is invalid");
        }
        assignFields(target, source);
        LOGGER.info("Bill with id " + id + " was saved");
        return target;
    }

    public void delete(long id) throws BillNotFoundException {
        if(isNull(billRepository.findById(id))){
            LOGGER.error("Bill Not Found");
            throw new BillNotFoundException("Item Not Found with Id " + id);
        }
        LOGGER.info("Bill with Id " + id + " was deleted");
        billRepository.deleteById(id);
    }

    private void assignFields(Bill target, Bill source) {
        target.setCfdiUse(source.getCfdiUse());
        target.setDate(source.getDate());
        target.setFolioFiscal(source.getFolioFiscal());
        target.setPdfURL(source.getPdfURL());
        target.setReceiverName(source.getReceiverName());
        target.setReceiverRFC(source.getReceiverRFC());
        target.setTransmitterName(source.getTransmitterName());
        target.setTransmitterRFC(source.getTransmitterRFC());
        target.setTotal(source.getTotal());
        target.setXmlURL(source.getXmlURL());
    }
}
