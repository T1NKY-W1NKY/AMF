package com.example.demo.dao;

import com.example.demo.dto.AMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AMFSQLDAO {

    @Autowired
    AMFRepository amfRepository;

//    gets amf object with id 4, all working amf entities from now all will be using 4 until that gets cleaned up
//        this is b/c there should only be one amf object and 4 just happened to be first seen amf id in db
    public AMF getAMF(){
        return amfRepository.findById(4).get();
    }

    public AMF save(AMF amf){
        AMF savedAMF = amfRepository.save(amf);
        return savedAMF;
    }

    //need annotation to know it is updating entity for some reason, do not need to .save() the object then
    @Transactional
    public AMF updateAMF(AMF amf){
        AMF updateAmf = amfRepository.findById(4).get();
        //cant just = to new amf object, have to change all the setters otherwise im guessing the id just gets overwritten
        updateAmf.setArenas(amf.getArenas());
        updateAmf.setArenasRanked(amf.getArenasRanked());
        updateAmf.setRanked(amf.getRanked());
        updateAmf.setBattleRoyale(amf.getBattleRoyale());
        return updateAmf;
    }
}
