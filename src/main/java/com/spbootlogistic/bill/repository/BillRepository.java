package com.spbootlogistic.bill.repository;

import com.spbootlogistic.bill.model.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
