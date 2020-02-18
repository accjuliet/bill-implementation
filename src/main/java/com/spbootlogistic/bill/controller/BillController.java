package com.spbootlogistic.bill.controller;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.model.Error;
import com.spbootlogistic.bill.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resources;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("bill")
public class BillController {
    @Autowired
    IBillService billService;

    @GetMapping
    public ResponseEntity findAll() {
        List<Bill> bills = (List<Bill>) billService.findAll();
        for (Bill bill: bills) {
            long billId = bill.getId();
            Link selfLink = linkTo(BillController.class).slash(billId).withSelfRel().withType("GET").withName("Find All");
            bill.add(selfLink);
        }
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        try {
            Bill bill = billService.findById(id);
            bill.add(linkTo(methodOn(BillController.class).findById(id)).withSelfRel().withType("GET").withName("Find by id"));
            return ResponseEntity.ok(bill);
        } catch (BillNotFoundException e) {
            return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Bill bill) {
        try {
            billService.save(bill);
            bill.add(linkTo(methodOn(BillController.class).save(bill)).withSelfRel().withType("POST").withName("Save"));
            return ResponseEntity.ok(bill);
        } catch (InvalidBillException e) {
            return new ResponseEntity(new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Bill bill) {
        try {
            billService.update(id, bill);
            bill.add(linkTo(methodOn(BillController.class).update(id, bill)).withSelfRel().withType("PUT").withName("Update"));
            return ResponseEntity.ok(bill);
        } catch (BillNotFoundException e) {
            return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        try {
            billService.delete(id);
            Link link = linkTo(methodOn(BillController.class).delete(id)).withSelfRel().withType("DELETE").withName("Delete");
            return ResponseEntity.accepted().body(link);
        } catch (BillNotFoundException e) {
            return new ResponseEntity(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
