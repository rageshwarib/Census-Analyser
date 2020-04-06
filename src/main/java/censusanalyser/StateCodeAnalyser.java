package censusanalyser;

import censusanalyser.IndiaStateCensusCSV;
import censusanalyser.IndiaStateCensusException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCodeAnalyser {
    public static int loadIndiaStateCensusData(String indiaStateCodeCsvFilePath) throws IndiaStateCensusException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeCsvFilePath));
            CsvToBeanBuilder<IndiaStateCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCensusCSV> indiaStateCodeCSVIterator = csvToBean.iterator();
            int namOfEateries = 0;
            // while (censusCSVIterator.hasNext()) {
            //  namOfEateries++;
            // IndiaCensusCSV censusData = censusCSVIterator.next();
            //  }
            Iterable<IndiaStateCensusCSV> indiaStateCodeCSVSIterable = () -> indiaStateCodeCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaStateCodeCSVSIterable.spliterator(), false).count();
            return namOfEateries;
        } catch (IOException e) {
            throw new IndiaStateCensusException(e.getMessage(),
                    IndiaStateCensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

}