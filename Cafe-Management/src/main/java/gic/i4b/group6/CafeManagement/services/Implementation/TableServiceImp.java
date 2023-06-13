package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.Orders;
import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.repositories.OrderRepository;
import gic.i4b.group6.CafeManagement.repositories.TableRepository;
import gic.i4b.group6.CafeManagement.services.TableService;

@Service
public class TableServiceImp implements TableService {
    
    private TableRepository tableRepository;
    private JdbcTemplate jdbcTemplate;
    private OrderRepository orderRepository;

    public TableServiceImp(TableRepository tableRepository, JdbcTemplate jdbcTemplate, OrderRepository orderRepository) {
        this.tableRepository = tableRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public void setTableNum(Integer number) {
        Long rowExisted = tableRepository.count();
        if (rowExisted > number) {
            for (int i = 1; i <= rowExisted; i++) {
                if (i > number) {
                    tableRepository.deleteById(i);
                }
            }
        } else if (rowExisted < number) {
            String sql = "ALTER TABLE tables AUTO_INCREMENT = 1";
            jdbcTemplate.execute(sql);

            for (int i = (int) (rowExisted + 1); i <= number; i++) {
                Tables table = new Tables();
                table.setNumber(i);
                table.setAvailability(1);
                tableRepository.save(table);
            }
        }
        Long newRowCount = tableRepository.count();
        if (newRowCount % 2 == 0) {
            for (int i = 1; i <= newRowCount; i++) {
                Tables table = tableRepository.findById(i).get();
                if (i <= newRowCount / 2) {
                    table.setAvailability(1);
                } else {
                    table.setAvailability(0);
                }
                tableRepository.save(table);
            }
        } else {
            for (int i = 1; i <= newRowCount; i++) {
                Tables table = tableRepository.findById(i).get();
                if (i <= (newRowCount / 2) + 1) {
                    table.setAvailability(1);
                } else {
                    table.setAvailability(0);
                }
                tableRepository.save(table);
            }
        }
    }

    @Override
    public Long getTableNum() {
        return tableRepository.count();
    }

    @Override
    public List<Tables> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public Tables getTableById(Integer id) {
        return tableRepository.findById(id).get();
    }

    @Override
    public void setAvailability(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();

        table.setAvailability(1);
        tableRepository.save(table);
    }

    @Override
    public void setUnavailibility(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();

        table.setAvailability(2);
        tableRepository.save(table);
    }

    @Override
    public void removeAllOrderByTableId(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();

        List<Orders> orderList = orderRepository.findByTables(table);

        for(Orders o : orderList) {
            if(o.getAddons() == null) {
                if(o != null && o.getDrinks() != null &&
                    o.getSizes() != null && o.getTables() != null) {

                    o.getDrinks().setOrders(null);
                    o.getSizes().setOrders(null);
                    o.getTables().setOrders(null);
                }
            }
            else{
                if(o != null && o.getDrinks() != null &&
                    o.getSizes() != null && o.getTables() != null) {

                    o.getAddons().setOrders(null);
                    o.getDrinks().setOrders(null);
                    o.getSizes().setOrders(null);
                    o.getTables().setOrders(null);
                }
            }
            orderRepository.delete(o);
        }
    }

    @Override
    public Integer countOrderByTableId(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();

        List<Orders> orderList = orderRepository.findByTables(table);
        return orderList.size();
    }
}


