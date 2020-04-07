package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public String SrNo;

    @CsvBindByName(column = "StateName", required = true)
    public String StateName ;

    @CsvBindByName(column = "TIN", required = true)
    public String TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public String StateCode;

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "Serial Number='" + SrNo + '\'' +
                ", State Name='" + StateName + '\'' +
                ", TIN='" + TIN + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
