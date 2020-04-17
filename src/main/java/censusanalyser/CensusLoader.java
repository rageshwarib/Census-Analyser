package censusanalyser;

import com.BridgeLabz.CensusAnalyserMyException;
import com.BridgeLabz.CsvBuilderFactoryInterface;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CensusLoader {
    Map<String, CensusDAO> censusDataMap = new HashMap<>();


    public <T> Map<String, CensusDAO> loadCensusData(String csvFilePath, Class<T> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvBuilderFactoryInterface csvBuilder = CsvBuilderFactory.getCsvBuilder();
            List<T> censusCSVList;
            censusCSVList = csvBuilder.getCsvList(reader , censusCSVClass);
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV"))
                censusCSVList.stream().filter(data -> data !=null).map(IndiaCensusCSV.class::cast).
                        forEach(data -> this.censusDataMap.put(data.population,new CensusDAO(data)));
            else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                censusCSVList.stream().filter(data -> data !=null).map(USCensusCSV.class::cast).
                        forEach(data -> this.censusDataMap.put(data.population,new CensusDAO(data)));
            }
            return censusDataMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CensusAnalyserMyException e) {
            System.out.println(e.getMessage());
            throw new CensusAnalyserException(e.getMessage(), e.type);
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
