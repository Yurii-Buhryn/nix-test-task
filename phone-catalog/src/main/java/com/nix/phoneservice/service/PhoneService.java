package com.nix.phoneservice.service;

import com.nix.phoneservice.domain.PhoneEntity;
import com.nix.phoneservice.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public Page<PhoneEntity> getAll(Integer page) {
        return phoneRepository.findAll(
                PageRequest.of(page, 10));
    }

    public Optional<PhoneEntity> findByUuid(String uuid) {
        return phoneRepository.findByUuid(uuid);
    }
}
