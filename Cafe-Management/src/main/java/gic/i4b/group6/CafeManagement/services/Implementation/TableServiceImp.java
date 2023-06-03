package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.repositories.TableRepository;
import gic.i4b.group6.CafeManagement.services.TableService;

@Service
public class TableServiceImp implements TableService {
    
    private TableRepository tableRepository;
    private JdbcTemplate jdbcTemplate;

    public TableServiceImp(TableRepository tableRepository, JdbcTemplate jdbcTemplate) {
        this.tableRepository = tableRepository;
        this.jdbcTemplate = jdbcTemplate;
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
}
