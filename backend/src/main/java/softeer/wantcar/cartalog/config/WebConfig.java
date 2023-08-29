package softeer.wantcar.cartalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://hyendei.shop",
                        "https://hyendei.shop:443",
                        "https://www.hyendei.shop",
                        "https://www.hyendei.shop:443",
                        "https://localhost:5173," +
                        "http://localhost:5173");
    }
}
