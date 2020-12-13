package com.android.homeit.common;

public class HouseSubmission {

    private String numberOfBedrooms, numberOfBathrooms, livingArea, lotArea, floors, yearBuild, yearRenovated, areaAbove, areaBasement, zipcode, latitude, longitude, livingArea2015, lotArea2015, houseView, waterfront, houseCondition;


    public HouseSubmission(){}

    public HouseSubmission(String numberOfBedrooms,String numberOfBathrooms, String livingArea, String lotArea, String floors, String yearBuild, String yearRenovated, String areaAbove, String areaBasement, String zipcode, String latitude, String longitude, String livingArea2015, String lotArea2015, String houseView, String waterfront, String houseCondition)
    {
        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfBedrooms = numberOfBedrooms;
        this.livingArea = livingArea;
        this.lotArea = lotArea;
        this.floors = floors;
        this.yearBuild = yearBuild;
        this.yearRenovated = yearRenovated;
        this.areaAbove = areaAbove;
        this.areaBasement = areaBasement;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.livingArea2015 = livingArea2015;
        this.lotArea2015 = lotArea2015;
        this.houseView = houseView;
        this.waterfront = waterfront;
        this.houseCondition = houseCondition;
    }


        public String getNumberOfBedrooms() {
            return numberOfBedrooms;
        }

        public void setNumberOfBedrooms(String numberOfBedrooms) {
            this.numberOfBedrooms = numberOfBedrooms;
        }

        public String getNumberOfBathrooms() {
            return numberOfBathrooms;
        }

        public void setNumberOfBathrooms(String numberOfBathrooms) {
            this.numberOfBathrooms = numberOfBathrooms;
        }

        public String getLivingArea() {
            return livingArea;
        }

        public void setLivingArea(String livingArea) {
            this.livingArea = livingArea;
        }

        public String getLotArea() {
            return lotArea;
        }

        public void setLotArea(String lotArea) {
            this.lotArea = lotArea;
        }

        public String getFloors() {
            return floors;
        }

        public void setFloors(String floors) {
            this.floors = floors;
        }

        public String getYearBuild() {
            return yearBuild;
        }

        public void setYearBuild(String yearBuild) {
            this.yearBuild = yearBuild;
        }

        public String getYearRenovated() {
            return yearRenovated;
        }

        public void setYearRenovated(String yearRenovated) {
            this.yearRenovated = yearRenovated;
        }

        public String getAreaAbove() {
            return areaAbove;
        }

        public void setAreaAbove(String areaAbove) {
            this.areaAbove = areaAbove;
        }

        public String getAreaBasement() {
            return areaBasement;
        }

        public void setAreaBasement(String areaBasement) {
            this.areaBasement = areaBasement;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLivingArea2015() {
            return livingArea2015;
        }

        public void setLivingArea2015(String livingArea2015) {
            this.livingArea2015 = livingArea2015;
        }

        public String getLotArea2015() {
            return lotArea2015;
        }

        public void setLotArea2015(String lotArea2015) {
            this.lotArea2015 = lotArea2015;
        }

        public String getHouseView() {
            return houseView;
        }

        public void setHouseView(String houseView) {
            this.houseView = houseView;
        }

        public String getWaterfront() {
            return waterfront;
        }

        public void setWaterfront(String waterfront) {
            this.waterfront = waterfront;
        }

        public String getHouseCondition() {
            return houseCondition;
        }

        public void setHouseCondition(String houseCondition) {
            this.houseCondition = houseCondition;
        }
    }

