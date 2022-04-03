package com.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vendor.entity.Vendor;
import com.vendor.entity.VendorMapper;
import com.vendor.service.VendorService;

@RestController
//@RequestMapping("vendor")
public class VendorController {
	
	@Value("${product.url}")
	private String productUrl;
	
	@Autowired
	private VendorService vendorService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public String check() {
		return "welcome!";
	}
	
	@PostMapping()
	public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
		Vendor newVendor=vendorService.createVendor(vendor);
		return new ResponseEntity<Vendor>(newVendor,HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable("id") long id) {
		Vendor vendor = vendorService.getVendorById(id);
		return new ResponseEntity<Vendor>(vendor, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) {
		Vendor updatedVendor=vendorService.updateVendor(vendor);
		return new ResponseEntity<Vendor>(updatedVendor,HttpStatus.OK);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Vendor> getVendorByName(@PathVariable("name") String name) {
		Vendor vendor = vendorService.getVendorByName(name);
		return new ResponseEntity<Vendor>(vendor, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Vendor>> getAllVendors(
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "4") int pageSize) {
		
		Page<Vendor> vendors = vendorService.getAllVendors(pageNo, pageSize);

		// header
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("totalElement",String.valueOf(vendors.getTotalElements()));
		headers.add("totalPages", String.valueOf(vendors.getTotalPages()));

		return new ResponseEntity<List<Vendor>>(vendors.getContent(),headers, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Vendor> deleteVendor(@PathVariable("id") long id) {
		vendorService.deleteVendor(id);
		restTemplate.delete(this.productUrl+"/vendor/" + id);
		return new ResponseEntity<Vendor>(HttpStatus.OK);
	}
	
	@GetMapping("/map")
	public ResponseEntity<List<VendorMapper>> getVendorMapper(){
		return new ResponseEntity<List<VendorMapper>>(vendorService.getVendorMapper(),HttpStatus.OK);
	}
}
