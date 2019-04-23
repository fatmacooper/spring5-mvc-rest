package com.orionsson.spring5mvcrest.api.v1.mapper;

import com.orionsson.spring5mvcrest.api.v1.model.VendorDTO;
import com.orionsson.spring5mvcrest.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    private static final String NAME = "my beatiful vendor";
    private VendorMapper vendorMapper;

    @Before
    public void setUp() throws Exception {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    public void testVendorToVendorDTO() throws Exception{
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(vendor.getName(),vendorDTO.getName());
    }

    @Test
    public void testVendorDTOToVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertEquals(vendor.getName(),vendorDTO.getName());
    }
}