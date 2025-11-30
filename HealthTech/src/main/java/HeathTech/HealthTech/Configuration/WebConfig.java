package HeathTech.HealthTech.Configuration;

//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // (ex: /healthtech/registration_user/user_register)
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // ✅ आपके React/Vite FrontEnd का Origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ सभी आवश्यक मेथड्स
                .allowedHeaders("*") // ✅ सभी हेडर (Authorization और Content-Type सहित)
                .allowCredentials(true); // यदि आप कुकीज़ या सेशन का उपयोग कर रहे हैं
    }
}
