package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {


    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCSVFile(csvFilePath);
        CsvBuilderFactoryInterface csvBuilderFactoryInterface = new CsvFileBuilder();
        Iterator<IndiaCensusCSV> indiaCensusCSVIterator;
       indiaCensusCSVIterator = csvBuilderFactoryInterface.getIterator(csvFilePath,IndiaCensusCSV.class);

            //Iterator<IndiaCensusCSV> indiaCensusCSVIterator = CsvBuilderFactoryInterface.getIterator(csvFilePath,IndiaCensusCSV.class);
            int numOfEntries = 0;

            Iterable<IndiaCensusCSV> indiaCensusCSVSIterable = () -> indiaCensusCSVIterator;

            numOfEntries = (int) StreamSupport.stream(indiaCensusCSVSIterable.spliterator(), false).count();
            return numOfEntries;
    }
    public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {
        this.checkValidCSVFile(indiaStateCodeCsvFilePath);
            CsvBuilderFactoryInterface csvBuilderFactoryInterface = new CsvFileBuilder();
            Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator ;
             indiaStateCodeCSVIterator = csvBuilderFactoryInterface.getIterator(indiaStateCodeCsvFilePath,IndiaStateCodeCSV.class);

            int numOfEntries = 0;

            Iterable<IndiaStateCodeCSV> indiaStateCodeCSVSIterable = () -> indiaStateCodeCSVIterator;

            numOfEntries = (int) StreamSupport.stream(indiaStateCodeCSVSIterable.spliterator(), false).count();
            return numOfEntries;

    }

   public void checkValidCSVFile(String csvFilePath) throws CensusAnalyserException {
       if (!csvFilePath.contains(".csv")) {
           throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
       }

   }
}