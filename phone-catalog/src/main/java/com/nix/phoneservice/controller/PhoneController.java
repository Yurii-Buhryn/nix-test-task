package com.nix.phoneservice.controller;

import com.nix.phoneservice.domain.PhoneEntity;
import com.nix.phoneservice.dto.PhoneDto;
import com.nix.phoneservice.service.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping("/api/phones")
    public ResponseEntity<Page<PhoneDto>> getPhones(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page
    ) {
        return ResponseEntity.ok(phoneService.getAll(page).map(PhoneEntity::toDto));
    }

    @GetMapping("/api/phones/{uuid}")
    public ResponseEntity<PhoneDto> getPhoneByUuid(@PathVariable("uuid") String uuid) {
        return phoneService.findByUuid(uuid)
                .map(phoneEntity ->
                        ResponseEntity.ok(phoneEntity.toDto())
                )
                .orElseThrow(() -> new RuntimeException("Can't find phone"));
    }
}
