package com.dinchan.model;

import com.dinchan.domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;
    private PaymentStatus status;

}
