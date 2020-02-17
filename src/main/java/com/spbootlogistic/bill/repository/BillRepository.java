package com.spbootlogistic.bill.repository;

import com.spbootlogistic.bill.model.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
