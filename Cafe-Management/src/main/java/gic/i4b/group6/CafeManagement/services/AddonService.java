package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.Addons;

public interface AddonService {
    Addons getAddonById(Integer id);
    List<Addons> getAllAddons();
}
