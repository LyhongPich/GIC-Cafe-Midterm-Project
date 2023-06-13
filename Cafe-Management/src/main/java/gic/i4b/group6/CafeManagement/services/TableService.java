package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.Tables;

public interface TableService {
    void setTableNum(Integer number);
    void setAvailability(Integer tableId);
    void setUnavailibility(Integer tableId);
    void removeAllOrderByTableId(Integer tableId);
    Tables getTableById(Integer id);
    Long getTableNum();
    List<Tables> getAllTables();
}
