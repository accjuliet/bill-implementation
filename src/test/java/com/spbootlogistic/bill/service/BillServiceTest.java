package com.spbootlogistic.bill.service;

import com.spbootlogistic.bill.exception.BillNotFoundException;
import com.spbootlogistic.bill.exception.InvalidBillException;
import com.spbootlogistic.bill.model.Bill;
import com.spbootlogistic.bill.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BillServiceTest {
    @Mock
    BillRepository repository;

    @InjectMocks
    IBillService service = new BillService();

    private Bill bill;
    private LinkedList<Bill> list;

    @BeforeEach
    public void init() {
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
    public void billServiceFindAllSuccessful() {
        when(repository.findAll()).thenReturn(list);
        LinkedList<Bill> test = new LinkedList<>();
        service.findAll().forEach(bill -> test.add(bill));
        assertFalse(test.isEmpty());
        verify(repository, atMost(1)).findAll();
    }

    @Test
    public void billServiceFindAllUnsuccessful() {
        when(repository.findAll()).thenReturn(new LinkedList<>());
        LinkedList<Bill> test = new LinkedList<>();
        service.findAll().forEach(bill -> test.add(bill));
        assertTrue(test.isEmpty());
    }

    @Test
    public void billServiceFindByIdSuccessful() throws BillNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(bill));
        Bill tested = service.findById(1L);
        assertNotNull(tested);
        verify(repository, atMost(1)).findById(1L);
    }

    @Test
    public void billServiceFindByIdUnsuccessful() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BillNotFoundException.class,()-> service.findById(1L));
        verify(repository, atMost(1)).findById(1L);
    }

    @Test
    public void billServiceSaveSuccess() throws InvalidBillException {
        when(repository.save(bill)).thenReturn(bill);
        Bill tested = service.save(bill);
        assertNotNull(bill);
        assertEquals(tested, bill);
        verify(repository, atMost(1)).save(bill);
    }

    @Test
    public void billServiceSaveUnsuccessful() {
        assertThrows(InvalidBillException.class, ()->service.save(null));
        verify(repository, atMost(1)).save(bill);
    }


    @Test
    public void billServiceUpdateSuccess() throws BillNotFoundException, InvalidBillException {
        Bill toUpdate = new Bill();
        toUpdate.setId(2L);
        when(repository.findById(2L)).thenReturn(Optional.of(toUpdate));
        when(repository.save(bill)).thenReturn(bill);
        Bill test = service.update(2L, bill);
        assertNotNull(test);
        verify(repository, atMost(1)).findById(2L);
        verify(repository, atMost(1)).save(bill);
    }


    @Test
    public void billServiceUpdateThrowsBillNotFoundException() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(BillNotFoundException.class,()-> service.findById(2L));

    }

    @Test
    void billServiceDeleteSuccess() throws BillNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(bill));
        service.delete(1L);
        verify(repository, atMost(1)).deleteById(1L);
    }

    @Test
    void billServiceDeleteUnsuccessful() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BillNotFoundException.class,()-> service.findById(1L));
        verify(repository, atMost(1)).deleteById(1L);
    }
}