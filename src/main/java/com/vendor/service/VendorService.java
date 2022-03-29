package com.vendor.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.vendor.entity.Vendor;
import com.vendor.entity.VendorMapper;

public interface VendorService {
	Vendor createVendor(Vendor vendor);
	Vendor updateVendor(Vendor vendor);
	Vendor getVendorById(long id);
	Vendor getVendorByName(String name);
	Page<Vendor> getAllVendors(int pageNo,int pageSize);
	void deleteVendor(long id);
	List<VendorMapper> getVendorMapper();
}
