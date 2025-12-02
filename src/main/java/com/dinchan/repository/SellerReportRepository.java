package com.dinchan.repository;

import com.dinchan.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
  SellerReport findBySellerId(Long sellerId);
}
