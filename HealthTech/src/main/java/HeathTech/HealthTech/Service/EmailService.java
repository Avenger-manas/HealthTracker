package HeathTech.HealthTech.Service;

import com.sendgrid.*; // SendGrid, Request, Response, Method Classes के लिए
import com.sendgrid.helpers.mail.Mail; // Mail Class के लिए
import com.sendgrid.helpers.mail.objects.Content; // Content Class के लिए
import com.sendgrid.helpers.mail.objects.Email; // Email Class के लिए

import org.springframework.beans.factory.annotation.Value; // @Value एनोटेशन के लिए
import org.springframework.stereotype.Service;

import java.io.IOException; // <--- Java I/O एक्सेप्शन हैंडलिंग के लिए आवश्यक

// बाकी कोड (आपकी @Service क्लास और sendMail विधि)
// ...
@Service
public class EmailService {
  // 1. SendGrid API Key को Environment Variable से प्राप्त करें
    @Value("${SPRING_MAIL_PASSWORD}") // आप Render पर यह Env Var उपयोग कर रहे हैं
    private String sendGridApiKey;
    
    // 2. Sender Address को Env Var से प्राप्त करें (यदि आवश्यक हो)
    @Value("${SPRING_MAIL_USERNAME}")
    private String senderEmail;

    public boolean sendMail(String to, String subject, String body) {
        
        // 3. SendGrid ऑब्जेक्ट API Key के साथ बनाएँ
        SendGrid sendGrid = new SendGrid(sendGridApiKey);

        Email from = new Email(senderEmail); // FROM Address
        Email toEmail = new Email(to);       // TO Address
        Content content = new Content("text/plain", body);
        
        // Dynamic Mail Object बनाएं
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            // 4. API कॉल करें
            Response response = sendGrid.api(request);

            // 5. Success/Error चेक करें
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                System.out.println("Email sent successfully via SendGrid API. Status: " + response.getStatusCode());
                return true;
            } else {
                System.err.println("SendGrid API Error. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
                return false;
            }
        } catch (IOException ex) {
            System.err.println("API Call Failed: " + ex.getMessage());
            return false;
        }
    }
}
