package com.nix.phonemarketgatewayservice.client;

import com.nix.phonemarketgatewayservice.dto.PageDto;
import com.nix.phonemarketgatewayservice.dto.PhoneDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("phone-catalog-service")
public interface PhoneCatalogClient {

    @ApiOperation(value = "Get all phones")
    @GetMapping("/api/phones?page={page}")
    PageDto<PhoneDto> readPhones(@RequestParam("page") Integer page);

    @ApiOperation(value = "Get phone by UUID")
    @GetMapping("/api/phones/{uuid}")
    PhoneDto readPhoneByUuid(@PathVariable("uuid") String uuid);
}
