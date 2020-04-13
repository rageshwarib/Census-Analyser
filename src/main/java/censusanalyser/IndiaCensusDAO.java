package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public String population;
    public String areaInSqKm;
    public String densityPerSqKm;
    public String SrNo;
    public String StateName;
    public String TIN;
    public String StateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV){
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }
    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){
        SrNo = indiaStateCodeCSV.SrNo;
        StateName = indiaStateCodeCSV.StateCode;
        TIN = indiaStateCodeCSV.TIN;
        StateCode = indiaStateCodeCSV.StateCode;
    }
}
