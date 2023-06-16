package gic.i4b.group6.CafeManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gic.i4b.group6.CafeManagement.models.Invoices;
import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.models.Users;

public interface InvoiceRepository extends JpaRepository<Invoices, Integer> {
    Invoices findByTables(Tables tables);
    List<Invoices> findByUsers(Users users);
    
}
