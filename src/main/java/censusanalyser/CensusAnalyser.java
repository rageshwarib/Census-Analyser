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
    List<CensusDAO> censusCSVList ;

   Map<String, CensusDAO> censusDataMap  = new HashMap<>() ;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException{
       censusDataMap = new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
       return censusDataMap.size();
   }

    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyserException {
        censusDataMap =  new CensusLoader().loadCensusData(usCensusCsvFilePath, USCensusCSV.class);
        return censusDataMap.size();
    }

    public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        this.loadIndiaCensusData(csvFilePath);
        List<CensusDAO> sortedList = this.censusDataMap.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.state.compareTo(csvData2.state)).collect(Collectors.toList());
        String sortedStateCodeCensusJson = new Gson().toJson(sortedList);
        //System.out.println(sortedStateCodeCensusJson);
        return sortedStateCodeCensusJson;
    }
    public String getStateCodeWiseSortedData(String indiaStateCodeCsvFilePath ) throws CensusAnalyserException {
        this.loadIndiaStateCodeCensusData(indiaStateCodeCsvFilePath);
        List<CensusDAO> sortedList = this.censusDataMap.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.StateCode.compareTo(csvData2.StateCode)).collect(Collectors.toList());
        String sortedStateCodeCensusJson = new Gson().toJson(sortedList);
        return sortedStateCodeCensusJson;
    }

    public String getUSSortedData(String usCensusCsvFilePath) throws CensusAnalyserException {
        this.loadUSCensusData(usCensusCsvFilePath);
        List<CensusDAO> sortedList = this.censusDataMap.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.population.compareTo(csvData2.population)).collect(Collectors.toList());
        String sortedUSCensusJson = new Gson().toJson(sortedList);
        return sortedUSCensusJson;
    }

    public int loadIndiaStateCodeCensusData(String StateCodeCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(StateCodeCsvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();
            List<IndiaStateCodeCSV> censusCSVList ;
            censusCSVList = csvBuilder.getCsvList(reader , IndiaStateCodeCSV.class);
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
}

