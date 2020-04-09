package censusanalyser;

import com.BridgeLabz.CensusAnalyserMyException;
import com.BridgeLabz.CsvBuilderFactoryInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCSVFile(csvFilePath);
        this.getStateWiseSortedData(csvFilePath);
        return 1;
    }

    public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {

        this.checkValidCSVFile(indiaStateCodeCsvFilePath);
        try (Reader reader =Files.newBufferedReader(Paths.get(indiaStateCodeCsvFilePath))) {
            CsvBuilderFactoryInterface csvBuilderFactoryInterface = CsvBuilderFactory.getCsvBuilder();
            Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator = csvBuilderFactoryInterface.getIterator(reader , IndiaStateCodeCSV.class);
            int numOfEntries = 0;
            Iterable<IndiaStateCodeCSV> indiaStateCodeCSVSIterable = () -> indiaStateCodeCSVIterator;
            numOfEntries = (int) StreamSupport.stream(indiaStateCodeCSVSIterable.spliterator(), false).count();
            return numOfEntries;
        }catch (IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CensusAnalyserMyException e){
            throw new CensusAnalyserException(e.getMessage(),e.type);
        }
        catch (RuntimeException e) {
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

     public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException{
         try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
             CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();
             List<IndiaCensusCSV> censusCSVList = csvBuilder.getCsvList(reader , IndiaCensusCSV.class);
             //Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(census -> census.state);
             List sortedList = censusCSVList.stream().sorted().collect(Collectors.toList());
             String SortedStateCensusJson = new Gson().toJson(sortedList);
             return SortedStateCensusJson;
         }catch (IOException e){
             throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
         }catch (CensusAnalyserMyException e){
             throw new CensusAnalyserException(e.getMessage() , e.type);
         }catch (RuntimeException e) {
             if (e.getMessage().contains("header!"))
                 throw new CensusAnalyserException(e.getMessage(),
                         CensusAnalyserMyException.ExceptionType.INVALID_FILE_HEADER);

             throw new CensusAnalyserException(e.getMessage(),
                     CensusAnalyserMyException.ExceptionType.INVALID_FILE_DATA);
         }

    }

}

