package com.giovane_allexandre.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.giovane_allexandre.dsmeta.entities.Sale;
import com.giovane_allexandre.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;

	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;

	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;

	@Autowired
	private SaleRepository saleRepository;

	public void sendSms(Long SaleId) {

		Sale sale = saleRepository.findById(SaleId).get();

		String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();

		String msg = String.format("O vendedor %s foi destaque em %s com um total de R$ %.2f", sale.getSellerName(),
				date, sale.getAmount());

		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from, msg).create();

		System.out.println(message.getSid());
	}
}
