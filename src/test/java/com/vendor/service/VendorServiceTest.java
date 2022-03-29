package com.vendor.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vendor.entity.Vendor;
import com.vendor.repository.VendorRepository;
import com.vendor.serviceImpl.VendorServiceImpl;
@ExtendWith(MockitoExtension.class)
class VendorServiceTest {

	@Mock
	private VendorRepository vendorRepository;
	
	@InjectMocks
	private VendorServiceImpl vendorService;
	
	private Vendor vendor;
	private Pageable pageable;
	
	@BeforeEach
	void setup() {
		vendor=new Vendor(1,"dell",2,"test address","some number");
		pageable=PageRequest.of(0, 4);
	}
	
	@AfterEach
	void tearup() {
		vendor=null;
		pageable=null;
	}
	
	@Test
	void createVendorTest() {
		when(vendorRepository.existsByNameIgnoreCase(any())).thenReturn(false);
		when(vendorRepository.save(any())).thenReturn(vendor);
		vendorService.createVendor(vendor);
		verify(vendorRepository,times(1)).save(any());
	}
	
	@Test
	void updateVendorTest() {
		when(vendorRepository.existsById(vendor.getId())).thenReturn(true);
		when(vendorRepository.existsByIdNotAndName(vendor.getId(),vendor.getName())).thenReturn(false);
		when(vendorRepository.save(any())).thenReturn(vendor);
		vendorService.updateVendor(vendor);
		verify(vendorRepository,times(1)).save(any());
	}
	
	@Test
	void getVendorByIdTest() {
		Optional<Vendor> res=Optional.of(vendor);
		when(vendorRepository.existsById(vendor.getId())).thenReturn(true);
		when(vendorRepository.findById(vendor.getId())).thenReturn(res);
		Vendor expectedVendor=vendorService.getVendorById(vendor.getId());
		assertEquals(expectedVendor.getId(),vendor.getId());
	}
	
	@Test
	void getAllVendorTest() {
		vendorService.getAllVendors(0, 4);
		verify(vendorRepository,times(1)).findAll(pageable);
	}
	
	@Test
	void deleteVendor() {
		when(vendorRepository.existsById(vendor.getId())).thenReturn(true);
		vendorService.deleteVendor(vendor.getId());
		verify(vendorRepository,times(1)).deleteById(vendor.getId());
	}
	

}
