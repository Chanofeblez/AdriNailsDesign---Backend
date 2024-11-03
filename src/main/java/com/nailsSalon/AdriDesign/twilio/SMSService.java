package com.nailsSalon.AdriDesign.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
  @Value("${TWILIO_ACCOUNT_SID}")
  private String accountSid;

  @Value("${TWILIO_AUTH_TOKEN}")
  private String authToken;

  @Value("${TWILIO_PHONE_NUMBER}")
  private String twilioPhoneNumber;

  public void sendSms(String to, String message) {
    // Inicializar Twilio dentro del método
    Twilio.init(accountSid, authToken);
    Message.creator(
      new PhoneNumber(to),
      new PhoneNumber(twilioPhoneNumber),
      message
    ).create();
  }
}
