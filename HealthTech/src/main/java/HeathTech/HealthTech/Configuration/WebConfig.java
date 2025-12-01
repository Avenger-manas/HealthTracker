package HeathTech.HealthTech.Configuration;

//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Override
public void addCorsMappings(CorsRegistry registry) {

    // 🛑 यहाँ Live URL जोड़ें
    String[] allowedOrigins = {
        "http://localhost:5173", // Local Development
        "https://health-tracker-frontend-2n7l.vercel.app" // Vercel Live Deployment
    };

    registry.addMapping("/**")
            .allowedOrigins(allowedOrigins) // <--- अब Array का उपयोग करें
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
}
}
