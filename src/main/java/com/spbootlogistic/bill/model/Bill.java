package com.spbootlogistic.bill.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Bill extends RepresentationModel<Bill> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private Long claimId;
    @Column
    @NotNull(message = "folio fiscal cannot be empty")
    private String folioFiscal;
    @Column
    @NotNull(message = "transmitter RFC cannot be empty")
    private String transmitterRFC;
    @Column
    @NotNull(message = "transmitter name cannot be empty")
    private String transmitterName;
    @Column
    @NotNull(message = "receiver RFC cannot be empty")
    private String receiverRFC;
    @Column
    @NotNull(message = "receiver Name cannot be empty")
    private String receiverName;
    @Column
    @NotNull(message = "total cannot be empty")
    private double total;
    @Column
    //@NotNull(message = "date cannot be empty")
    @CreationTimestamp
    private LocalDate date;
    @Column
    @NotNull(message = "cfdi cannot be empty")
    private String cfdiUse;
    @Column
    @NotNull(message = "xmlUrl cannot be empty")
    private String xmlURL;
    @Column
    @NotNull(message = "pdfUrl cannot be empty")
    private String pdfURL;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFolioFiscal() {
        return folioFiscal;
    }

    public void setFolioFiscal(String folioFiscal) {
        this.folioFiscal = folioFiscal;
    }

    public String getTransmitterRFC() {
        return transmitterRFC;
    }

    public void setTransmitterRFC(String transmitterRFC) {
        this.transmitterRFC = transmitterRFC;
    }

    public String getTransmitterName() {
        return transmitterName;
    }

    public void setTransmitterName(String transmitterName) {
        this.transmitterName = transmitterName;
    }

    public String getReceiverRFC() {
        return receiverRFC;
    }

    public void setReceiverRFC(String receiverRFC) {
        this.receiverRFC = receiverRFC;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCfdiUse() {
        return cfdiUse;
    }

    public void setCfdiUse(String cfdiUse) {
        this.cfdiUse = cfdiUse;
    }

    public String getXmlURL() {
        return xmlURL;
    }

    public void setXmlURL(String xmlURL) {
        this.xmlURL = xmlURL;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }
}
