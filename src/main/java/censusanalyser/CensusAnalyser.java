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

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> indiaCensusCSVIterator = this.getIterator(reader, IndiaCensusCSV.class);
            int namOfEateries = 0;
            //Iterator<IndiaCensusCSV> censusCSVIterator = indiaCensusCSVIterator.iterator();
            Iterable<IndiaCensusCSV> indiaCensusCSVSIterable = () -> indiaCensusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaCensusCSVSIterable.spliterator(), false).count();
            return namOfEateries;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DATA);
        }
        catch (IOException e) {
        throw new CensusAnalyserException(e.getMessage(),
                CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
        public int loadIndiaStateCodeCensusData(String indiaStateCodeCsvFilePath) throws CensusAnalyserException {
            this.checkValidCSVFile(indiaStateCodeCsvFilePath);
            try {
                Reader reader = Files.newBufferedReader(Paths.get(indiaStateCodeCsvFilePath));

                Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator = this.getIterator(reader , IndiaStateCodeCSV.class);

                int namOfEateries = 0;

                Iterable<IndiaStateCodeCSV> indiaStateCodeCSVSIterable = () -> indiaStateCodeCSVIterator;
                namOfEateries = (int) StreamSupport.stream(indiaStateCodeCSVSIterable.spliterator(), false).count();
                return namOfEateries;

            }catch (RuntimeException e) {
                if (e.getMessage().contains("header!"))
                    throw new CensusAnalyserException(e.getMessage(),
                            CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);

                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_DATA);
            }
            catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
        }
        public void checkValidCSVFile(String csvFilePath) throws CensusAnalyserException{
            if (!csvFilePath.contains(".csv")) {
                throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
            }

        }

    public <T> Iterator getIterator(Reader reader,Class classFile){
        CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);

        csvToBeanBuilder.withType(classFile);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);

        CsvToBean<T> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }


}
