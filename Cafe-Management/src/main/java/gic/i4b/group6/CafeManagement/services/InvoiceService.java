package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.Invoices;

public interface InvoiceService {
    void setInvoice(Integer tableId, Integer cashierId);
    Invoices getInvoiceByTableId(Integer tableId);
    List<Integer> getLastInvoice();
}
