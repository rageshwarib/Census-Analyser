package censusanalyser;

public class CensusDAO {
    public String state;
   public String population;
    public String totalArea;
    public String populationDensity;
    public String StateCode;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV){
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
    }
    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){
        StateCode = indiaStateCodeCSV.StateCode;
    }
    public CensusDAO(USCensusCSV usCensusCSV){
        state = usCensusCSV.state;
        StateCode = usCensusCSV.stateId;
        population = usCensusCSV.population;

    }
}
