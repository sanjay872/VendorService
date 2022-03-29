package com.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vendor.entity.Vendor;
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	boolean existsByNameIgnoreCase(String name);

	Vendor findByName(String name);

	boolean existsByIdNotAndName(long id, String name);

	Vendor findByNameIgnoreCase(String name);

	Vendor findByNameIgnoreCaseContaining(String name);

	@Query("select v.name from Vendor v")
	List<String> getAllNames();

}
