package ar.edu.itba.paw.interfaces;

import java.util.Date;

public interface VerificationTokenService {
    String createVerificationToken(int userId);
}
