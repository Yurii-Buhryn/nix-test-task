package com.nix.phoneservice;

import com.nix.phoneservice.domain.PhoneEntity;
import com.nix.phoneservice.service.PhoneService;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@NoArgsConstructor
public class PhoneServiceTest {

    // In case of real application test data should be created separately in test configs
    // not in global liquibase evolutions

    @Autowired
    private PhoneService phoneService;

    @Test
    public void findPhoneByUuid() {
        Optional<PhoneEntity> phone = phoneService.findByUuid("3716a81b-4092-478c-a2d1-e8f847c864a4");
        assertTrue(phone.isPresent());
        assertEquals(phone.get().getName(), "Iphone XS");
        assertEquals(phone.get().getDescription(), "The last version of Iphone");
        assertEquals(phone.get().getPrice(), Double.valueOf(1000));
        assertEquals(phone.get().getImage().getId(), Long.valueOf(1));
    }

    @Test
    public void findPhoneByInvalidUuid() {
        Optional<PhoneEntity> phone = phoneService.findByUuid("invalid-uuid");
        assertFalse(phone.isPresent());
    }

    @Test
    public void getAllPhones() {
        Page<PhoneEntity> phonePage = phoneService.getAll(0);
        assertEquals(phonePage.getTotalElements(), 3);
        assertEquals(phonePage.getContent().size(), 3);
        assertEquals(phonePage.getContent().size(), 3);
    }
}
