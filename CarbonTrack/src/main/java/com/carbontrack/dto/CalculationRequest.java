package com.carbontrack.dto;

public class CalculationRequest {
    private String householdName;
    private double electricityKwh;
    private double lpgKg;
    private double petrolLitres;
    private double dieselLitres;
    private double waterLitres;
    private double wasteKg;

    public String getHouseholdName() { return householdName; }
    public void setHouseholdName(String householdName) { this.householdName = householdName; }
    public double getElectricityKwh() { return electricityKwh; }
    public void setElectricityKwh(double electricityKwh) { this.electricityKwh = electricityKwh; }
    public double getLpgKg() { return lpgKg; }
    public void setLpgKg(double lpgKg) { this.lpgKg = lpgKg; }
    public double getPetrolLitres() { return petrolLitres; }
    public void setPetrolLitres(double petrolLitres) { this.petrolLitres = petrolLitres; }
    public double getDieselLitres() { return dieselLitres; }
    public void setDieselLitres(double dieselLitres) { this.dieselLitres = dieselLitres; }
    public double getWaterLitres() { return waterLitres; }
    public void setWaterLitres(double waterLitres) { this.waterLitres = waterLitres; }
    public double getWasteKg() { return wasteKg; }
    public void setWasteKg(double wasteKg) { this.wasteKg = wasteKg; }
}
