package com.gfg.product.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Configuration
@NoArgsConstructor
public class AppConfiguration {

	@Value("${seller.notification.type}")
	private String sellerNotificationType;

}
