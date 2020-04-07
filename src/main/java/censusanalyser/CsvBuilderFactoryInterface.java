package censusanalyser;

import java.util.Iterator;

public interface CsvBuilderFactoryInterface {
    public <T> Iterator getIterator(String csvFilePath, Class classFile) throws CensusAnalyserException;

}

