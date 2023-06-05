package gic.i4b.group6.CafeManagement.services;

import java.time.LocalDateTime;
import java.util.List;

import gic.i4b.group6.CafeManagement.models.History;

public interface HistoryService {
    void setHistory(Integer orderNum, Integer tableNum, Float price, LocalDateTime orderDate);
    List<History> getAllHistory();
}
