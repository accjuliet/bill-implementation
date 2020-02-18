package com.spbootlogistic.bill.controller;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.model.Error;
import com.spbootlogistic.bill.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        try {
            Bill bill = billService.findById(id);
            bill.add(linkTo(methodOn(BillController.class).findById(id)).withSelfRel());
            return ResponseEntity.ok(bill);
        } catch (BillNotFoundException e) {
            return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Bill bill) {
        try {
            billService.save(bill);
            bill.add(linkTo(methodOn(BillController.class).save(bill)).withSelfRel());
            return ResponseEntity.ok(bill);
        } catch (InvalidBillException e) {
            return new ResponseEntity(new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Bill bill) {
        try {
            billService.update(id, bill);
            bill.add(linkTo(methodOn(BillController.class).update(id, bill)).withSelfRel());
            return ResponseEntity.ok(bill);
        } catch (BillNotFoundException e) {
            return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            billService.delete(id);
            return ResponseEntity.accepted().body("Item with Id " + id + " was deleted");
        } catch (BillNotFoundException e) {
            return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }
}
