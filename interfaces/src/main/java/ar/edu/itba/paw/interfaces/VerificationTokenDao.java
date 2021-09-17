package ar.edu.itba.paw.interfaces;

import java.util.Date;

public interface VerificationTokenDao {
    void createVerificationToken(int userId, String token, Date expiryDate);
}
