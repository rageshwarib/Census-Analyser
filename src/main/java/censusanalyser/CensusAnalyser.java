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

   Map<String, IndiaCensusDAO> censusData = new HashMap<>();
   Map<String, IndiaCensusDAO> censusStateCodeData = new HashMap<>();

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
      // this.checkValidCSVFile(csvFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();

            List<IndiaCensusCSV> censusCSVList = new ArrayList<IndiaCensusCSV>();
            censusCSVList = csvBuilder.getCsvList(reader , IndiaCensusCSV.class);
            this.CensusListconvertIntoMap(censusCSVList);
            return censusCSVList.size();
        }catch (IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CensusAnalyserMyException e){
            throw new CensusAnalyserException(e.getMessage() , e.type);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserMyException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserMyException.ExceptionType.INVALID_FILE_DATA);
        }
    }
    private void CensusListconvertIntoMap(List<IndiaCensusCSV> indiaCensusCSV){
        indiaCensusCSV.stream().filter(data -> data !=null).
                forEach(data -> this.censusData.put(data.densityPerSqKm,new IndiaCensusDAO(data)));
    }

    public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {
       // this.checkValidCSVFile(indiaStateCodeCsvFilePath);
        try (Reader reader =Files.newBufferedReader(Paths.get(indiaStateCodeCsvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();

            List<IndiaStateCodeCSV> censusCSVCodeList = new ArrayList<IndiaStateCodeCSV>();
            censusCSVCodeList = csvBuilder.getCsvList(reader , IndiaStateCodeCSV.class);
            this.stateCodeListconvertIntoMap(censusCSVCodeList);
            return censusCSVCodeList.size();
        }catch (IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CensusAnalyserMyException e){
            throw new CensusAnalyserException(e.getMessage(),e.type);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserMyException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserMyException.ExceptionType.INVALID_FILE_DATA);
        }
    }

    private void stateCodeListconvertIntoMap(List<IndiaStateCodeCSV> censusCSVCodeList){
        censusCSVCodeList.stream().filter(data -> data != null).
                forEach(data -> this.censusStateCodeData.put(data.StateCode,new IndiaCensusDAO( data )));
    }
    public void checkValidCSVFile(String csvFilePath) throws CensusAnalyserException {
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

     public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException{
        this.loadIndiaCensusData(csvFilePath);
        List<IndiaCensusDAO> sortedList = this.censusData.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.densityPerSqKm.compareTo(csvData2.densityPerSqKm)).collect(Collectors.toList());
         String sortedStateCensusJson = new Gson().toJson(sortedList);
         return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedData(String indiaStateCodeCsvFilePath ) throws CensusAnalyserException {
        this.loadIndiaStateCodeCensusData(indiaStateCodeCsvFilePath);
        List<IndiaCensusDAO> sortedList = this.censusStateCodeData.values().stream().
                sorted((csvData1 , csvData2) -> csvData1.StateCode.compareTo(csvData2.StateCode)).collect(Collectors.toList());
        String sortedStateCodeCensusJson = new Gson().toJson(sortedList);
        return sortedStateCodeCensusJson;
    }
}

