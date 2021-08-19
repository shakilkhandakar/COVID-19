package com.example.covid_19;

public class Data {
    private String flag,country,continent,population,tests,updated,cases,todayCases,todayRecovered,deaths,todayDeaths,recovered,active,critical;

    public Data() {

    }

    public Data(String flag, String country, String continent, String population, String tests, String updated,
                String cases, String todayCases, String todayRecovered, String deaths, String todayDeaths,
                String recovered, String active, String critical) {
        this.flag = flag;
        this.country = country;
        this.continent = continent;
        this.population = population;
        this.tests = tests;
        this.updated = updated;
        this.cases = cases;
        this.todayCases = todayCases;
        this.todayRecovered = todayRecovered;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;
    }

    public String getFlag() {
        return flag;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public String getPopulation() {
        return population;
    }

    public String getTests() {
        return tests;
    }

    public String getUpdated() {
        return updated;
    }

    public String getCases() {
        return cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getActive() {
        return active;
    }

    public String getCritical() {
        return critical;
    }
}
