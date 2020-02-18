package com.spbootlogistic.bill.service;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;

public interface IBillService {
    Iterable<Bill> findAll();

    public Bill findById(long id) throws BillNotFoundException;

    public Bill save(Bill bill) throws InvalidBillException;

    public Bill update(long id, Bill bill) throws BillNotFoundException;

    public void delete(long id) throws BillNotFoundException;
}
