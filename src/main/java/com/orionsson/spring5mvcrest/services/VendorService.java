package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.model.VendorDTO;
import com.orionsson.spring5mvcrest.api.v1.model.VendorListDTO;

public interface VendorService {
    VendorListDTO getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createVendor(VendorDTO vendorDTO);
    void deleteVendorById(Long id);
    VendorDTO patchVendorByDTO(Long id, VendorDTO VendorDTO);
    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);
}
