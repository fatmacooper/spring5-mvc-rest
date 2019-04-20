package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.orionsson.spring5mvcrest.api.v1.model.VendorDTO;
import com.orionsson.spring5mvcrest.api.v1.model.VendorListDTO;
import com.orionsson.spring5mvcrest.controllers.v1.VendorController;
import com.orionsson.spring5mvcrest.domain.Vendor;
import com.orionsson.spring5mvcrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceTest {
    private String NAME = "Healthy Pets";
    private Long ID = 1L;

    VendorMapper vendorMapper;
    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorMapper = VendorMapper.INSTANCE;
        vendorService = new VendorServiceImpl(vendorRepository,vendorMapper);
    }

    @Test
    public void testGetAllVendors() throws Exception{
        //given
        given(vendorRepository.findAll()).willReturn(Arrays.asList(getVendor1(),getVendor2()));
        //when
        VendorListDTO vendorListDTO = vendorService.getAllVendors();
        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorListDTO.getVendors().size(), is(equalTo(2)));
    }

    @Test
    public void testGetVendorById() throws Exception{
        Vendor vendor1 = getVendor1();
        given(vendorRepository.findById(anyLong())).willReturn(Optional.ofNullable(vendor1));
        VendorDTO vendorDTO = vendorService.getVendorById(ID);
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(vendorDTO.getName(),is(equalTo(NAME)));
    }
    @Test
    public void testCreateVendor() throws Exception{
        //given
        Vendor vendor = getVendor1();
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
        //when
        VendorDTO savedVendorDTO = vendorService
                                .createVendor(vendorMapper.vendorToVendorDTO(vendor));
        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorURL(), containsString("1"));
    }
    @Test
    public void testDeleteVendorById() throws Exception{
        vendorService.deleteVendorById(ID);
        verify(vendorRepository,times(1)).deleteById(anyLong());
    }
    @Test
    public void testPatchVendorByDTO() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = getVendor1();
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        VendorDTO savedVendorDTO = vendorService.patchVendorByDTO(ID,vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorURL(),containsString("1"));
    }
    @Test
    public void testSaveVendorByDTO() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = getVendor1();
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID,vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getVendorURL(),containsString("1"));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetVendorByIdNotFound() throws Exception {
        //given
        //mockito BBD syntax since mockito 1.10.0
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

    }

    private Vendor getVendor1() {
        Vendor vendor1 = new Vendor();
        vendor1.setId(ID);
        vendor1.setName(NAME);
        return vendor1;
    }

    private Vendor getVendor2() {
        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Phillips");
        return vendor2;
    }
}