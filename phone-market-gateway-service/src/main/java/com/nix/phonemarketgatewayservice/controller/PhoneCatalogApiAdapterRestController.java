package com.nix.phonemarketgatewayservice.controller;

import com.nix.phonemarketgatewayservice.client.PhoneCatalogClient;
import com.nix.phonemarketgatewayservice.dto.PageDto;
import com.nix.phonemarketgatewayservice.dto.PhoneDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PhoneCatalogApiAdapterRestController {

    private final PhoneCatalogClient phoneCatalogClient;

    @GetMapping("/api/phones")
    public ResponseEntity<PageDto<PhoneDto>> getPhones(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        return ResponseEntity.ok(phoneCatalogClient.readPhones(page));
    }

    @GetMapping("/api/phones/{uuid}")
    public ResponseEntity<PhoneDto> getPhoneByUuid(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(phoneCatalogClient.readPhoneByUuid(uuid));
    }

}
