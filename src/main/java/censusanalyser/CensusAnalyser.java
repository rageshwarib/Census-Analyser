package censusanalyser;

import com.BridgeLabz.CensusAnalyserMyException;
import com.BridgeLabz.CsvBuilderFactoryInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

   Map<String, CensusDAO> censusData = new HashMap<>();
   Map<String, CensusDAO> censusStateCodeData = new HashMap<>();

    public <T> int loadCensusData(String csvFilePath, Class<T> censusCSVClass) throws CensusAnalyserException {
      this.checkValidCSVFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();

            List<T> censusCSVList ;
            censusCSVList = csvBuilder.getCsvList(reader , censusCSVClass);
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV"))
            censusCSVList.stream().filter(data -> data !=null).map(IndiaCensusCSV.class::cast).
                    forEach(data -> this.censusData.put(data.state,new CensusDAO(data)));
            if (censusCSVClass.getName().equals("censusanalyser.IndiaStateCodeCSV")) {
                censusCSVList.stream().filter(data -> data !=null).map(IndiaStateCodeCSV.class::cast).
                        forEach(data -> this.censusStateCodeData.put(data.StateCode,new CensusDAO(data)));
                return censusCSVList.size();
            }
            else if (censusCSVClass.getName().equals("censusAnalyser.USCensusCSV")) {
                censusCSVList.stream().filter(data -> data !=null).map(USCensusCSV.class::cast).
                        forEach(data -> this.censusData.put(data.state,new CensusDAO(data)));
                return censusCSVList.size();
            }
            return censusCSVList.size();
        } catch (IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CensusAnalyserMyException e) {
            System.out.println(e.getMessage());
            throw new CensusAnalyserException(e.getMessage(),e.type);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA);
        }

    }
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException{
       return this.loadCensusData(csvFilePath, IndiaCensusCSV.class);
   }
    public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException{
        return this.loadCensusData(indiaStateCodeCsvFilePath, IndiaStateCodeCSV.class);
    }
    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(usCensusCsvFilePath, USCensusCSV.class);
    }


    public void checkValidCSVFile(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

     public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException{
        this.loadIndiaCensusData(csvFilePath);
        List<CensusDAO> sortedList = this.censusData.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.state.compareTo(csvData2.state)).collect(Collectors.toList());
         String sortedStateCensusJson = new Gson().toJson(sortedList);
         return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedData(String indiaStateCodeCsvFilePath ) throws CensusAnalyserException {
        this.loadIndiaStateCodeCensusData(indiaStateCodeCsvFilePath);
        List<CensusDAO> sortedList = this.censusStateCodeData.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.StateCode.compareTo(csvData2.StateCode)).collect(Collectors.toList());
        String sortedStateCodeCensusJson = new Gson().toJson(sortedList);
        return sortedStateCodeCensusJson;
    }
}

