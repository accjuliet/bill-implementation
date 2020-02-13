package com.spbootlogistic.bill.service;

import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService{

    @Autowired
    BillRepository billRepository;

    public Iterable<Bill> findAll(){
        return billRepository.findAll();
    }

    public Bill findById(long id){
        return billRepository.findById(id).get();
    }

    public Bill save(Bill bill){
        return billRepository.save(bill);
    }

    public Bill update(long id, Bill bill){
        Bill toUpdate = billRepository.findById(id).get();
        bill.setId(id);
        billRepository.deleteById(id);
        return billRepository.save(bill);
    }

    public void delete(long id){
        billRepository.deleteById(id);
    }
}
