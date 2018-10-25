package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondHandListingRepository extends JpaRepository<EstateSecondHandListing, Integer> {
}
