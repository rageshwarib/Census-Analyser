package censusanalyser;

public class IndiaStateCensusException extends Exception{

    enum ExceptionType {

        CENSUS_FILE_PROBLEM ;
    }
    ExceptionType type;

    public IndiaStateCensusException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public IndiaStateCensusException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

