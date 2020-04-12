package censusanalyser;

import com.BridgeLabz.CensusAnalyserMyException;
import com.BridgeLabz.CsvBuilderFactoryInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
   // List<IndiaCensusCSV> censusCSVList = new ArrayList<IndiaCensusCSV>();
   // List<IndiaStateCodeCSV> censusCSVCodeList = new ArrayList<IndiaStateCodeCSV>();
    Map<String , IndiaCensusCSV> indiaCensusCSVMap = new HashMap<>();
    Map<String , IndiaStateCodeCSV> indiaStateCodeCSVMap = new HashMap<>();

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCSVFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();
            indiaCensusCSVMap = csvBuilder.getCsvMap(reader, IndiaCensusCSV.class);
            return indiaCensusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CensusAnalyserMyException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserMyException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserMyException.ExceptionType.INVALID_FILE_DATA);
        }
    }

    public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {
        this.checkValidCSVFile(indiaStateCodeCsvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeCsvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();

            indiaStateCodeCSVMap = csvBuilder.getCsvMap(reader, IndiaStateCodeCSV.class);
            return indiaStateCodeCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CensusAnalyserMyException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserMyException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserMyException.ExceptionType.INVALID_FILE_DATA);
        }

    }
    public void checkValidCSVFile(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }

    }
    public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException {
        this.loadIndiaCensusData(csvFilePath);
        List<IndiaCensusCSV> sortedMap = this.indiaCensusCSVMap.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.state.compareTo(csvData2.state)).collect(Collectors.toList());
        String sortedStateCensusJson = new Gson().toJson(sortedMap);
        return sortedStateCensusJson;
    }
    public String getCodeWiseSortedData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {
        this.loadIndiaStateCodeCensusData(indiaStateCodeCsvFilePath);
        List<IndiaStateCodeCSV> sortedList = this.indiaStateCodeCSVMap.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.StateCode.compareTo(csvData2.StateCode)).collect(Collectors.toList());
        String sortedStateCodeCensusJson = new Gson().toJson(sortedList);
        return sortedStateCodeCensusJson;
    }
}