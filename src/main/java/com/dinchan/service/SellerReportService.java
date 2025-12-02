package com.dinchan.service;

import com.dinchan.model.SellerReport;

public interface SellerReportService {
  SellerReport getSellerReport(Long sellerId);
  SellerReport updateSellerReport(SellerReport sellerReport);

}
