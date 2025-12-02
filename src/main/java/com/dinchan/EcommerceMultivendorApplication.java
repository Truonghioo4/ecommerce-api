package com.dinchan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.LogbackMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( exclude = {
        JvmMetricsAutoConfiguration.class,
        LogbackMetricsAutoConfiguration.class,
        MetricsAutoConfiguration.class
})
public class EcommerceMultivendorApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceMultivendorApplication.class, args);
    }
}
