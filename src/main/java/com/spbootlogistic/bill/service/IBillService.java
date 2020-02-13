package com.spbootlogistic.bill.service;

import com.spbootlogistic.bill.model.Bill;

public interface IBillService {
    Iterable<Bill> findAll();

    public Bill findById(long id);

    public Bill save(Bill bill);

    public Bill update(long id, Bill bill);

    public void delete(long id);
}
