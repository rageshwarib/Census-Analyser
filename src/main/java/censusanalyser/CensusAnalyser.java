package censusanalyser;

import com.BridgeLabz.CensusAnalyserMyException;
import com.BridgeLabz.CsvBuilder;
import com.BridgeLabz.CsvBuilderFactoryInterface;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {


    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCSVFile(csvFilePath);
        try
                (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

        CsvBuilderFactoryInterface csvBuilderFactoryInterface = CsvBuilderFactory.getCsvBuilder();
        Iterator<IndiaCensusCSV> indiaCensusCSVIterator = csvBuilderFactoryInterface.getIterator(reader, IndiaCensusCSV.class);

        int numOfEntries = 0;

        Iterable<IndiaCensusCSV> indiaCensusCSVSIterable = () -> indiaCensusCSVIterator;

        numOfEntries = (int) StreamSupport.stream(indiaCensusCSVSIterable.spliterator(), false).count();
        return numOfEntries;
    }catch (IOException e){
                    throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CensusAnalyserMyException e){
                    throw new CensusAnalyserException(e.getMessage() , e.type);
        }
    }

    public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {

        this.checkValidCSVFile(indiaStateCodeCsvFilePath);
        try (Reader reader =Files.newBufferedReader(Paths.get(indiaStateCodeCsvFilePath))) {
            CsvBuilderFactoryInterface csvBuilderFactoryInterface = CsvBuilderFactory.getCsvBuilder();
            Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator =csvBuilderFactoryInterface.getIterator(reader , IndiaStateCodeCSV.class);

            int numOfEntries = 0;

            Iterable<IndiaStateCodeCSV> indiaStateCodeCSVSIterable = () -> indiaStateCodeCSVIterator;

            numOfEntries = (int) StreamSupport.stream(indiaStateCodeCSVSIterable.spliterator(), false).count();
            return numOfEntries;

    }catch (IOException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CensusAnalyserMyException e){
            throw new CensusAnalyserException(e.getMessage(),e.type);
        }
    }

   public void checkValidCSVFile(String csvFilePath) throws CensusAnalyserException {
       if (!csvFilePath.contains(".csv")) {
           throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
       }

   }
}