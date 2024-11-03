package com.nailsSalon.AdriDesign.twilio;

import com.nailsSalon.AdriDesign.appointment.AppointmentService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

  private static final Logger logger = LoggerFactory.getLogger(SMSService.class);
  @Value("${TWILIO_ACCOUNT_SID}")
  private String accountSid;

  @Value("${TWILIO_AUTH_TOKEN}")
  private String authToken;

  @Value("${TWILIO_PHONE_NUMBER}")
  private String twilioPhoneNumber;

  public void sendSms(String to, String message) {

    // Inicializar Twilio dentro del método
    Twilio.init(accountSid, authToken);
    logger.info("to: {}", to);
    logger.info("message: {}", message);
    logger.info("accountSid: {}", accountSid);
    logger.info("authToken: {}", authToken);
    logger.info("twilioPhoneNumber: {}", twilioPhoneNumber);
    Message.creator(
      new PhoneNumber(to),
      new PhoneNumber(twilioPhoneNumber),
      message
    ).create();
  }
}
