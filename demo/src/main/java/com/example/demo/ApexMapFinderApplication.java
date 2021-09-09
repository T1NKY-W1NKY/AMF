package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ApexMapFinderApplication {

	private static final Logger log = LoggerFactory.getLogger(ApexMapFinderApplication.class);

	public static void main(String[] args) {

		//Line belows runs the String[] args part with the beans below main method
		SpringApplication.run(ApexMapFinderApplication.class, args);

		WebClient webClient = WebClient.create();
//				.builder()
//				.baseUrl("https://api.mozambiquehe.re/maprotation?version=2&auth=XxaZO6hTfymkQoBqNqlg")
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				.build();

//This prints out JSON payload as a String

//		String jsonString = webClient.get()
//				.uri("https://api.mozambiquehe.re/maprotation?version=2&auth=XxaZO6hTfymkQoBqNqlg")
//				.retrieve()
//				.bodyToMono(String.class)
//				.block();
//		log.info(jsonString);
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			AMF amfPOJO = mapper.readValue(jsonString, AMF.class);
//			log.info(amfPOJO.getArenas().getCurrent().getMap());
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

//		String jsonString = webClient.get()
//				.uri("https://api.mozambiquehe.re/maprotation?version=2&auth=XxaZO6hTfymkQoBqNqlg")
//				.exchange()
//				.block()
//				.bodyToMono(String.class)
//				.block();
//		log.info(jsonString);
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			AMF amfPOJO = mapper.readValue(jsonString, AMF.class);
//			log.info(amfPOJO.getArenas().getCurrent().getMap());
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

	}

//	//Can I replace these with what works, making the webclient and such "Beans"?
//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
//
////	@Bean
////	public WebClient webClient(WebClient builder) {
////		return builder.build();
////	}
//
//	@Bean
//	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//		return args -> {
//			AMF amf = restTemplate.getForObject(
//					"https://api.mozambiquehe.re/maprotation?version=2&auth=XxaZO6hTfymkQoBqNqlg",
//					AMF.class);
//			log.info(amf.toString());
//		};
//
//	}

}
