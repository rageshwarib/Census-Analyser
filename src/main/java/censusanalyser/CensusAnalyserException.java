package censusanalyser;

import com.BridgeLabz.CensusAnalyserMyException;

public class CensusAnalyserException extends Exception {

     enum ExceptionType {
        CENSUS_FILE_PROBLEM ,
        INVALID_FILE_TYPE,
        INVALID_FILE_DATA ,
        INVALID_FILE_HEADER ;
    }

   public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
    public CensusAnalyserException(String message, CensusAnalyserMyException.ExceptionType type){
        super(message);
        this.type = ExceptionType.valueOf(type.name());
    }
}
