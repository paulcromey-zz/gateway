package gateway;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // tag::route-locator[]
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		return builder
				.routes()
				.route(p -> p.path("/users").uri(uriConfiguration.getUserServiceURL()))
				.build();
	}
    // end::route-locator[]
}

// tag::uri-configuration[]
@ConfigurationProperties
class UriConfiguration {
    
    private String serverAddressURL = "http://localhost";
    
    private Integer userServicePort = 8090;
    
    private String userServiceURL = serverAddressURL.concat(":").concat(userServicePort.toString());

    public String getUserServiceURL() {
		return userServiceURL;
	}

	public void setUserServiceURL(String userServiceURL) {
		this.userServiceURL = userServiceURL;
	}

}
// end::uri-configuration[]
// end::code[]