package com.mystorege;

public class PostAdd {
    String pId;
    String storeType;
    double dimensions;
    String date;
    String time;
    String storeFeatures;
    int monthlyRental;
    String notes;
    String reportName;

    public PostAdd() {
    }

    public PostAdd(String pId, String storeType, double dimensions, String date, String time, String storeFeatures, int monthlyRental, String notes, String reportName) {
        this.pId = pId;
        this.storeType = storeType;
        this.dimensions = dimensions;
        this.date = date;
        this.time = time;
        this.storeFeatures = storeFeatures;
        this.monthlyRental = monthlyRental;
        this.notes = notes;
        this.reportName = reportName;
    }

    public String getpId() {
        return pId;
    }

    public String getStoreType() {
        return storeType;
    }

    public double getDimensions() {
        return dimensions;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStoreFeatures() {
        return storeFeatures;
    }

    public int getMonthlyRental() {
        return monthlyRental;
    }

    public String getNotes() {
        return notes;
    }

    public String getReportName() {
        return reportName;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setDimensions(double dimensions) {
        this.dimensions = dimensions;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStoreFeatures(String storeFeatures) {
        this.storeFeatures = storeFeatures;
    }

    public void setMonthlyRental(int monthlyRental) {
        this.monthlyRental = monthlyRental;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
