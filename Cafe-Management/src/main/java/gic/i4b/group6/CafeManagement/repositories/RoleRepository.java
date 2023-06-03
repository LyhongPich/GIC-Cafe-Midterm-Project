package gic.i4b.group6.CafeManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import gic.i4b.group6.CafeManagement.models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    
}
