package br.com.adtimbo.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adtimbo.api.service.notification.NotificationService;

@SpringBootTest
class ApiApplicationTests {
	
	@Autowired
	NotificationService notificationService;

	@Test
	void contextLoads() {
	}

}
