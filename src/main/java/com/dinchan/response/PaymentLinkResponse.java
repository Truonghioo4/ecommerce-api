package com.dinchan.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentLinkResponse {
  private String code;
  private String message;
  private String payment_link_url;
  private String payment_link_id;
}
