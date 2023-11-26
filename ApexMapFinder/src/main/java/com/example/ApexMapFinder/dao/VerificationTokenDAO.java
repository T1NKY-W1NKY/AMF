package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationTokenDAO {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    public VerificationToken getVerificationToken(String token){
        return verificationTokenRepository.findByToken(token);
    }

    public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    public void delete(int notificationId){
        verificationTokenRepository.delete(verificationTokenRepository.findByNotificationId(notificationId));
    }
}
