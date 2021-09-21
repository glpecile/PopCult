package ar.edu.itba.paw.interfaces;

import java.util.Map;

public interface EmailService {
    public void sendEmail(String to, String subject, String template, Map<String, Object> variables);
}
