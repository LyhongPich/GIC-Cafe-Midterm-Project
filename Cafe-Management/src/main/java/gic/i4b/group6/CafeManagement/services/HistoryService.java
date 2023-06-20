package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.History;

public interface HistoryService {
    void setHistory(Integer tableId);
    List<History> getAllHistory();
}
