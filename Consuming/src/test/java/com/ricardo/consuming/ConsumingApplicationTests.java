package com.ricardo.consuming;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ConfigurationHello.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource("/application.properties")
class ConsumingApplicationTests {
	
	@Autowired
	private SOAPConnectClient client;
	
	@Value("${WSDL.SOAPClient}")
	private String clientEndPoint;
	
	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	
	@Before
	public void init() throws Exception {
		marshaller.setPackagesToScan("io.spring.guides.gs_producing_web_service");
		marshaller.afterPropertiesSet();
	}

	@Test
	void unLockUser() {
		GetCountryRequest request = new GetCountryRequest();
		request.setName("Spain");
		GetCountryResponse response = (GetCountryResponse) client.callWebServices(clientEndPoint, request);
		assertEquals(true, response.getCountry());
	}

}
