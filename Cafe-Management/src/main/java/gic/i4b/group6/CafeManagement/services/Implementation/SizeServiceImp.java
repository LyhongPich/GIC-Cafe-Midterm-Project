package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.Sizes;
import gic.i4b.group6.CafeManagement.repositories.SizeRepository;
import gic.i4b.group6.CafeManagement.services.SizeService;

@Service
public class SizeServiceImp implements SizeService {

    private SizeRepository sizeRepository;

    public SizeServiceImp(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Sizes> getAllSize() {
        return sizeRepository.findAll();
    }

    @Override
    public Sizes getSizeById(Integer id) {
        return sizeRepository.findById(id).get();
    }
    
}
