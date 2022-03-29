package com.vendor.serviceImpl;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vendor.entity.Vendor;
import com.vendor.entity.VendorMapper;
import com.vendor.exception.CustomException;
import com.vendor.repository.VendorRepository;
import com.vendor.service.VendorService;
@Service
@Transactional
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorRepository vendorRepository;
	
	@Override
	public Vendor createVendor(Vendor vendor) {
		if(!vendorRepository.existsByNameIgnoreCase(vendor.getName()))
			return vendorRepository.save(vendor);
		else
			throw new CustomException("Vendor already exist");
	}

	@Override
	public Vendor updateVendor(Vendor vendor) {
		if(vendorRepository.existsById(vendor.getId()))
			if(!vendorRepository.existsByIdNotAndName(vendor.getId(),vendor.getName()))
				return vendorRepository.save(vendor);
			else
				throw new CustomException("vendor name already exist");
		else
			throw new CustomException("vendor doesn't exist");
	}

	@Override
	public Vendor getVendorByName(String name) {
		return vendorRepository.findByNameIgnoreCase(name);
	}
	
	@Override
	public Page<Vendor> getAllVendors(int pageNo,int pageSize) {
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Vendor> page=vendorRepository.findAll(pageable);
		return page;
	}

	@Override
	public void deleteVendor(long id) {
		if(vendorRepository.existsById(id))
			vendorRepository.deleteById(id);
		else
			throw new CustomException("vendor doesn't exist");
	}

	@Override
	public Vendor getVendorById(long id) {
		if(vendorRepository.existsById(id)) 
			return vendorRepository.findById(id).get();
		throw new CustomException("vendor not found");
	}

	@Override
	public List<VendorMapper> getVendorMapper() {
		List<VendorMapper> res=new ArrayList<>();
		for(Vendor vendor:vendorRepository.findAll()) {
			VendorMapper map=new VendorMapper();
			map.setId(vendor.getId());
			map.setName(vendor.getName());
			res.add(map);
		}
		return res;
	}
}
