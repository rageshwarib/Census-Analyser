package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population", required = true)
    public String population;

    @CsvBindByName(column = "Total area", required = true)
    public String totalArea;

    @CsvBindByName(column = "Population Density", required = true)
    public String populationDensity;




}
