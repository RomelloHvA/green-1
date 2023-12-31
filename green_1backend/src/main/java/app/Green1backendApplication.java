package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Green1backendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Green1backendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String frontendUrl = System.getenv("ALLOWED_ORIGIN");
                registry.addMapping("/**").allowedOrigins(frontendUrl)
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
