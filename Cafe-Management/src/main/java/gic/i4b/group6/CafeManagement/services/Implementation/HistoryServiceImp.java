package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.History;
import gic.i4b.group6.CafeManagement.models.Orders;
import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.repositories.HistoryRepository;
import gic.i4b.group6.CafeManagement.repositories.OrderRepository;
import gic.i4b.group6.CafeManagement.repositories.TableRepository;
import gic.i4b.group6.CafeManagement.services.HistoryService;

@Service
public class HistoryServiceImp implements HistoryService {

    private HistoryRepository historyRepository;
    private TableRepository tableRepository;
    private OrderRepository orderRepository;
    

    public HistoryServiceImp(HistoryRepository historyRepository, TableRepository tableRepository, OrderRepository orderRepository) {
        this.historyRepository = historyRepository;
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void setHistory(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();

        List<Orders> orderList = orderRepository.findByTables(table);

        for(Orders o : orderList) {
            History history = new History();
            history.setOrder_date(o.getOrder_date());
            history.setOrder_num(o.getId());
            history.setPrice(o.getTotal_price());
            history.setTable_num(o.getTables().getNumber());
            historyRepository.save(history);
        }
    }

    @Override
    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }
    
}
