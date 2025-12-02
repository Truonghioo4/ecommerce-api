package com.dinchan.service.impl;

import com.dinchan.model.SellerReport;
import com.dinchan.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportImpl implements SellerReportService {

  @Override
  public SellerReport getSellerReport(Long sellerId) {
    return null;
  }

  @Override
  public SellerReport updateSellerReport(SellerReport sellerReport) {
    return null;
  }
} 
