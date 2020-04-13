package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public String population;
    public String totalArea;
    public String populationDensity;
    public String StateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV){
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
    }
    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){
        StateCode = indiaStateCodeCSV.StateCode;
    }
    public IndiaCensusDAO(USCensusCSV usCensusCSV){
        state = usCensusCSV.state;
        StateCode = usCensusCSV.stateId;
        population = usCensusCSV.population;

    }
}
