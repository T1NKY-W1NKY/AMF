package com.example.ApexMapFinder;

import com.example.ApexMapFinder.dao.AMFSQLDAO;
import com.example.ApexMapFinder.dto.AMF;
import com.example.ApexMapFinder.service.AMFService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ApexMapFinderApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void whenMapsUpdate_UsersWhoMustBeEmailedAreIdentified(){
		givenMapsUpdate();
		whenUserNotificationsWithNewMapsAreIdentified();
		thenAnEmailIsSentToThem();
	}


	private void givenMapsUpdate() {

	}

	private void whenUserNotificationsWithNewMapsAreIdentified() {

	}

	private void thenAnEmailIsSentToThem() {

	}
}
