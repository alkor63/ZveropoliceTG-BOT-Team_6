package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
}
