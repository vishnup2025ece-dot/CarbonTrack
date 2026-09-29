package com.carbontrack.service;

import com.carbontrack.dto.CalculationRequest;
import com.carbontrack.model.CarbonCalculation;
import org.springframework.stereotype.Service;

@Service
public class CarbonCalculatorService {

    // Educational prototype factors: kg CO2e per unit.
    private static final double ELECTRICITY_FACTOR = 0.82;
    private static final double LPG_FACTOR = 3.00;
    private static final double PETROL_FACTOR = 2.31;
    private static final double DIESEL_FACTOR = 2.68;
    private static final double WATER_FACTOR = 0.0003;
    private static final double WASTE_FACTOR = 0.50;

    public CarbonCalculation calculate(CalculationRequest request) {
        CarbonCalculation c = new CarbonCalculation();

        c.setHouseholdName(request.getHouseholdName());
        c.setElectricityKwh(nonNegative(request.getElectricityKwh()));
        c.setLpgKg(nonNegative(request.getLpgKg()));
        c.setPetrolLitres(nonNegative(request.getPetrolLitres()));
        c.setDieselLitres(nonNegative(request.getDieselLitres()));
        c.setWaterLitres(nonNegative(request.getWaterLitres()));
        c.setWasteKg(nonNegative(request.getWasteKg()));

        double total =
                c.getElectricityKwh() * ELECTRICITY_FACTOR +
                c.getLpgKg() * LPG_FACTOR +
                c.getPetrolLitres() * PETROL_FACTOR +
                c.getDieselLitres() * DIESEL_FACTOR +
                c.getWaterLitres() * WATER_FACTOR +
                c.getWasteKg() * WASTE_FACTOR;

        c.setTotalCo2(Math.round(total * 100.0) / 100.0);
        return c;
    }

    private double nonNegative(double value) {
        return Math.max(0, value);
    }
}
