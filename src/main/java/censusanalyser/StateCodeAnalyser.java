package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCodeAnalyser {

    public int loadIndiaStateCensusData(String indiaStateCodeCsvFilePath) throws StateCodeAnalyserException {
            if( !indiaStateCodeCsvFilePath.contains(".csv")) {
                throw new StateCodeAnalyserException("Invalid file type", StateCodeAnalyserException.ExceptionType.INVALID_FILE_TYPE);
            }
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
            throw new StateCodeAnalyserException(e.getMessage(),
                    StateCodeAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new StateCodeAnalyserException(e.getMessage(),
                        StateCodeAnalyserException.ExceptionType.INVALID_FILE_HEADER);

            throw new StateCodeAnalyserException(e.getMessage(),
                    StateCodeAnalyserException.ExceptionType.INVALID_FILE_DATA);
        }

    }

}