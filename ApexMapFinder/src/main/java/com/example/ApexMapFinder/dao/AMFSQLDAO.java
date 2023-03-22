package com.example.ApexMapFinder.dao;

import com.example.ApexMapFinder.dto.AMF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AMFSQLDAO {

    @Autowired
    AMFRepository amfRepository;

//    gets amf object with id 1, all working amf entities from now all will be using 4 until that gets cleaned up
//        this is b/c there should only be one amf object and 1 just happened to be first seen amf id in db
    public AMF getAMF(){
        return amfRepository.findById(0).get();
    }

    public AMF save(AMF amf){
        AMF savedAMF = amfRepository.save(amf);
        return savedAMF;
    }

    //need annotation to know it is updating entity for some reason, do not need to .save() the object then
    @Transactional
    public AMF updateAMF(AMF newAmf){
        AMF amf;
        //would it be better to just call the code within getAmf() ?
        try {
            amf = getAMF();
        }
        catch (Exception e){
            return save(newAmf);
        }
        //have to go and set every non-id data type otherwise new instances of those objects in db will be created
        //this will leave the db with many leftover values from the past
        amf.getArenasRanked().getCurrent().setRemainingTimer(newAmf.getArenasRanked().getCurrent().getRemainingTimer());
        amf.getArenasRanked().getCurrent().setMap(newAmf.getArenasRanked().getCurrent().getMap());
        amf.getArenasRanked().getNext().setRemainingTimer(newAmf.getArenasRanked().getNext().getRemainingTimer());
        amf.getArenasRanked().getNext().setMap(newAmf.getArenasRanked().getNext().getMap());
        amf.getArenas().getCurrent().setRemainingTimer(newAmf.getArenas().getCurrent().getRemainingTimer());
        amf.getArenas().getCurrent().setMap(newAmf.getArenas().getCurrent().getMap());
        amf.getArenas().getNext().setRemainingTimer(newAmf.getArenasRanked().getNext().getRemainingTimer());
        amf.getArenas().getNext().setMap(newAmf.getArenas().getNext().getMap());
        amf.getBattleRoyale().getCurrent().setRemainingTimer(newAmf.getBattleRoyale().getCurrent().getRemainingTimer());
        amf.getBattleRoyale().getCurrent().setMap(newAmf.getBattleRoyale().getCurrent().getMap());
        amf.getBattleRoyale().getNext().setRemainingTimer(newAmf.getBattleRoyale().getNext().getRemainingTimer());
        amf.getBattleRoyale().getNext().setMap(newAmf.getBattleRoyale().getNext().getMap());
        amf.getRanked().getCurrent().setRemainingTimer(newAmf.getRanked().getCurrent().getRemainingTimer());
        amf.getRanked().getCurrent().setMap(newAmf.getRanked().getCurrent().getMap());
        amf.getRanked().getNext().setRemainingTimer(newAmf.getRanked().getNext().getRemainingTimer());
        amf.getRanked().getNext().setMap(newAmf.getRanked().getNext().getMap());
//        List<State> currentStates = amf.getStates();
//        List<State> newStates = newAmf.getStates();
//        //cant just = to new amf object, have to change all the setters otherwise im guessing the id just gets overwritten
//        for(int i = 0; i < currentStates.size(); i++){
//            currentStates.get(i).setMap(newStates.get(i).getMap());
//            currentStates.get(i).setRemainingTimer(newStates.get(i).getRemainingTimer());
//        }
//        amf.setArenas(newAmf.getArenas());
//        amf.setArenasRanked(newAmf.getArenasRanked());
//        amf.setRanked(newAmf.getRanked());
//        amf.setBattleRoyale(newAmf.getBattleRoyale());
        return amf;
    }
}
