package com.nix.phoneservice;

import com.nix.phoneservice.controller.PhoneController;
import com.nix.phoneservice.domain.ImageEntity;
import com.nix.phoneservice.domain.PhoneEntity;
import com.nix.phoneservice.service.PhoneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneController.class)
public class PhoneControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhoneService phoneService;

    @Test
    public void findPhoneByUuid() throws Exception {

        ImageEntity foundImage = new ImageEntity();
        foundImage.setId(1L);
        foundImage.setUuid("dc89cc72-7ff0-40c8-a23a-c95ca533524a");

        PhoneEntity foundPhone = new PhoneEntity();
        foundPhone.setId(1L);
        foundPhone.setUuid("3716a81b-4092-478c-a2d1-e8f847c864a4");
        foundPhone.setName("Iphone XS");
        foundPhone.setDescription("Iphone XS");
        foundPhone.setPrice(1000D);
        foundPhone.setImage(foundImage);

        given(phoneService.findByUuid(any())).willReturn(Optional.of(foundPhone));

        mvc.perform(get("/api/phones/{uuid}", "3716a81b-4092-478c-a2d1-e8f847c864a4")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(foundPhone.getName()))
                .andExpect(jsonPath("$.price").value(foundPhone.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(foundImage.url()));
    }

    @Test
    public void findPhoneByInvalidUuid() throws Exception {
        given(phoneService.findByUuid("invalid-uuid")).willReturn(Optional.empty());

        mvc.perform(get("/api/phones/{uuid}", "invalid-uuid")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }
}
