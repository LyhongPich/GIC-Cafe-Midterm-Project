package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.Invoices;
import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.models.Users;
import gic.i4b.group6.CafeManagement.repositories.InvoiceRepository;
import gic.i4b.group6.CafeManagement.repositories.TableRepository;
import gic.i4b.group6.CafeManagement.repositories.UserRepository;
import gic.i4b.group6.CafeManagement.services.InvoiceService;

@Service
public class InvoiceServiceImp implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private TableRepository tableRepository;
    private UserRepository userRepository;
    private UserServiceImp userServiceImp;

    public InvoiceServiceImp(InvoiceRepository invoiceRepository, TableRepository tableRepository,
            UserRepository userRepository, UserServiceImp userServiceImp) {
        this.invoiceRepository = invoiceRepository;
        this.tableRepository = tableRepository;
        this.userRepository = userRepository;
        this.userServiceImp = userServiceImp;
    }

    @Override
    public void setInvoice(Integer tableId, Integer cashierId) {

        int state = 0;
        List<Invoices> invoices = invoiceRepository.findAll();
        for (Invoices i : invoices) {
            if (i.getTables() != null && i.getTables().getId() == tableId &&
                    i.getUsers() != null && i.getUsers().getId() == cashierId) {
                state = 1;
                break;
            }
        }

        if (state == 0) {
            Invoices invoice = new Invoices();
            Tables table = tableRepository.findById(tableId).get();
            Users user = userRepository.findById(cashierId).get();

            invoice.setTables(table);
            invoice.setUsers(user);
            invoiceRepository.save(invoice);
        }
    }

    @Override
    public Invoices getInvoiceByTableId(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();
        return invoiceRepository.findByTables(table);
    }

    @Override
    public List<Integer> getLastInvoice() {

        List<Users> userList = userServiceImp.getAllCashier();
        List<Integer> invNum = new ArrayList<>();
        for (Users user : userList) {
            int max = 0;
            List<Invoices> invoiceList = invoiceRepository.findByUsers(user);
            for (Invoices inv : invoiceList) {
                if (inv != null && inv.getId() > max) {
                    max = inv.getId();
                }
            }
            invNum.add(max);
        }
        return invNum;
    }

}
