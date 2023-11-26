package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);

    VerificationToken findByNotificationId(int id);
}

