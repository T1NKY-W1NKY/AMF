package com.example.demo.dao;

import com.example.demo.dto.AMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class AMFSQLDAO {

    @Autowired
    AMFRepository amfRepository;

//    gets amf object with id number 0
//        this is b/c there should only be one amf object and i think 0 is the starting id??
    public AMF getAMF(){
        return amfRepository.findById(0).get();
    }

    public AMF save(AMF amf){
        AMF savedAMF = amfRepository.save(amf);
        return savedAMF;
    }
    //miggle moggle
}
