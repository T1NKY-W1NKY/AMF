package com.example.ApexMapFinder.dto;

import com.example.ApexMapFinder.dao.VerificationTokenRepository;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class VerificationToken {

    //number of minutes in a day.. token expires after one day (24 hours)
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Notification.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "notification_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Notification notification;

    //not sure if this will be created upon object creation
    private Date expiryDate = calculateExpiryDate(EXPIRATION);

    private Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken(Notification newNotification){
        notification =  newNotification;
        expiryDate = calculateExpiryDate(EXPIRATION);
        token = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Notification getNotification() {
        return notification;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public VerificationToken(){

    }

}
