package com.gfg.product.service.impl;

import org.springframework.stereotype.Service;

import com.gfg.product.configuration.AppConfiguration;
import com.gfg.product.entity.Product;
import com.gfg.product.notification.EmailSender;
import com.gfg.product.notification.SmsSender;
import com.gfg.product.service.NotificationService;
import com.gfg.product.shared.NotificationType;

@Service
public class NotificationServiceImpl implements NotificationService {

	private AppConfiguration appConfig;

	private EmailSender emailSender;

	private SmsSender smsSender;

	public NotificationServiceImpl(AppConfiguration appConfig, EmailSender emailSender, SmsSender smsSender) {
		super();
		this.appConfig = appConfig;
		this.emailSender = emailSender;
		this.smsSender = smsSender;
	}

	@Override
	public void sendPriceChangeWarning(Product product) {

		if (appConfig.getSellerNotificationType().equals(NotificationType.SMS.toString()))
			smsSender.sendPriceChangeWarning(product);
		else if (appConfig.getSellerNotificationType().equals(NotificationType.EMAIL.toString()))
			emailSender.sendPriceChangeWarning(product);

	}

}
