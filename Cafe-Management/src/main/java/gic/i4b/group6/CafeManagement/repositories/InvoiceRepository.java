package gic.i4b.group6.CafeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import gic.i4b.group6.CafeManagement.models.Invoices;
import gic.i4b.group6.CafeManagement.models.Tables;

public interface InvoiceRepository extends JpaRepository<Invoices, Integer> {
    Invoices findByTables(Tables tables);
}
