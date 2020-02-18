package com.spbootlogistic.bill.controller;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.service.IBillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class BillControllerTest {
    @Mock
    IBillService service;

    @InjectMocks
    BillController controller;

    private Bill bill;
    private LinkedList<Bill> list;


    @BeforeEach
    void init() {
        bill = new Bill();
        list = new LinkedList<>();
        bill.setId(1L);
        bill.setCfdiUse("USOS EN GENERAL");
        bill.setDate(LocalDate.now());
        bill.setFolioFiscal("ASDFGH-466S-ASDAS");
        bill.setPdfURL("algo.com");
        bill.setReceiverName("Julieta");
        bill.setReceiverRFC("JLT-8975");
        bill.setTotal(80.00);
        bill.setTransmitterName("Erick");
        bill.setTransmitterRFC("ERDF-589");
        bill.setXmlURL("sad.com");

        list.add(bill);
    }

    @Test
    public void billControllerFindAllSuccess() {
        when(service.findAll()).thenReturn(list);
        LinkedList<Bill> test = new LinkedList<>();
        service.findAll().forEach(bill -> test.add(bill));
        ResponseEntity responseEntity = controller.findAll();
        verify(service, atMost(2)).findAll();
        assertEquals(responseEntity, controller.findAll());
    }

    @Test
    public void billControllerFindAllUnsuccessful() {
        when(service.findAll()).thenReturn(new LinkedList<>());
        LinkedList<Bill> test = new LinkedList<>();
        service.findAll().forEach(bill -> test.add(bill));
        ResponseEntity responseEntity = controller.findAll();
        verify(service, atMost(2)).findAll();
        assertEquals(responseEntity, controller.findAll());
        assertTrue(test.isEmpty());
    }

    @Test
    public void billControllerFindByIdSuccess() throws BillNotFoundException {
        when(service.findById(1L)).thenReturn(bill);
        ResponseEntity responseEntity = controller.findById(1L);
        verify(service, atMost(1)).findById(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity, controller.findById(1L));
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerFindByIdUnsuccessful() throws BillNotFoundException {
        when(service.findById(1L)).thenThrow(new BillNotFoundException("Exception called"));
        ResponseEntity responseEntity = controller.findById(1L);
        verify(service, atMost(1)).findById(1L);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerSaveSuccess() throws InvalidBillException {
        when(service.save(bill)).thenReturn(bill);
        ResponseEntity responseEntity = controller.save(bill);
        verify(service, atMost(1)).save(bill);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerSaveUnsuccessful() throws InvalidBillException {
        when(service.save(null)).thenThrow(new InvalidBillException("Exception called"));
        ResponseEntity responseEntity = controller.save(null);
        verify(service, atMost(1)).save(bill);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerUpdateSuccess() throws BillNotFoundException {
        when(service.update(2L,bill)).thenReturn(bill);
        ResponseEntity responseEntity = controller.update(2L,bill);
        verify(service, atMost(1)).update(2L,bill);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerUpdateUnsuccessful() throws BillNotFoundException {
        when(service.update(2L,bill)).thenThrow(new BillNotFoundException("Update Exception"));
        ResponseEntity responseEntity = controller.update(2L,bill);
        verify(service, atMost(1)).update(2L,bill);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.NOT_FOUND);
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerDeleteSuccess() throws BillNotFoundException {
        ResponseEntity responseEntity = controller.delete(1L);
        verify(service, atMost(1)).delete(1L);
        assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
    }

    @Test
    public void billControllerDeleteUnsuccessful() throws BillNotFoundException {
        doThrow(new BillNotFoundException("Delete")).when(service).delete(anyLong());
        ResponseEntity responseEntity = controller.delete(anyLong());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
    }
}