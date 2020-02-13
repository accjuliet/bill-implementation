package com.spbootlogistic.bill.controller;

import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bill")
public class BillController {
    @Autowired
    IBillService billService;

    @GetMapping
    public ResponseEntity findAll() {
        Iterable<Bill> it = billService.findAll();
        return ResponseEntity.ok(it);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        Bill bill = billService.findById(id);
        return ResponseEntity.ok(bill);

    }

    @PostMapping
    public ResponseEntity save(@RequestBody Bill bill) {
        billService.save(bill);
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Bill bill) {
        billService.update(id, bill);
        return ResponseEntity.ok(bill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        Bill bill = billService.findById(id);
        billService.delete(id);
        return ResponseEntity.ok(bill);
    }














    }
