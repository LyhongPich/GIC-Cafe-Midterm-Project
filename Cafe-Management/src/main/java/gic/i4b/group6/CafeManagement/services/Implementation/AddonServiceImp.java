package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.Addons;
import gic.i4b.group6.CafeManagement.repositories.AddonRepository;
import gic.i4b.group6.CafeManagement.services.AddonService;

@Service
public class AddonServiceImp implements AddonService {

    private AddonRepository addonRepository;

    public AddonServiceImp(AddonRepository addonRepository) {
        this.addonRepository = addonRepository;
    }

    @Override
    public List<Addons> getAllAddons() {
        return addonRepository.findAll();
    }

    @Override
    public Addons getAddonById(Integer id) {
        return addonRepository.findById(id).get();
    }
    
}
