package gateway;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.UUID;

/**
 * @author Paul Cromey
 */
// tag::code[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		properties = {"userServiceURL=http://localhost:8080"})
@AutoConfigureWireMock(port = 0)
public class ApplicationTest {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void contextLoads() throws Exception {
		
		String id = UUID.randomUUID().toString();
		//Stubs
		stubFor(get(urlEqualTo("/users/" + id))
				.willReturn(aResponse()
					.withStatus(404)
					.withHeader("Content-Type", "application/json")));
		
		webClient
			.get().uri("/users" + id)
			.exchange()
			.expectStatus().isNotFound();

	}
}
// end::code[]