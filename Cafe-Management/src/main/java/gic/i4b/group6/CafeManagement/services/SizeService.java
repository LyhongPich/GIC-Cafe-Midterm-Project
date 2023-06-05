package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.Sizes;

public interface SizeService {
    Sizes getSizeById(Integer id);
    List<Sizes> getAllSize();
}
