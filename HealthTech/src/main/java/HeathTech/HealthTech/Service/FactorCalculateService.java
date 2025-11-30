package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.factorcalculate;
import org.springframework.stereotype.Service;

@Service
public class FactorCalculateService {

        public String calculateFactor(factorcalculate request) {
            double desiredIncrease = 0;

            switch (request.getSituation().toLowerCase()) {
                case "minor":
                    desiredIncrease = 30;
                    break;
                case "moderate":
                    desiredIncrease = 40;
                    break;
                case "major":
                    desiredIncrease = 50;
                    break;
                default:
                    return "Invalid situation. Use minor, moderate, or major.";
            }

            double requiredUnits;
            String factorType = request.getFactorType().toUpperCase();

            switch (factorType) {
                case "VIII":
                    int weight = Integer.parseInt(request.getWeight());
                    requiredUnits = weight * desiredIncrease * 0.5;
                    break;
                case "IX":
                    int w = Integer.parseInt(request.getWeight());
                    requiredUnits = w * desiredIncrease * 1.2;
                    break;
                default:
                    return "Currently only Factor VIII and IX are supported.";
            }

            // Vial sizes 250 IU और 500 IU
            int vials250 = (int) Math.ceil(requiredUnits / 250);
            int vials500 = (int) Math.ceil(requiredUnits / 500);

            return String.format(
                    "✅ Patient needs %.0f IU of Factor %s (Hemophilia %s).\nVials (250 IU each): %d\nVials (500 IU each): %d",
                    requiredUnits, factorType, request.getHeamophilia_type().toUpperCase(), vials250, vials500
            );
        }
    }
