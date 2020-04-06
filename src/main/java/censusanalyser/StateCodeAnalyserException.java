package censusanalyser;

public class StateCodeAnalyserException extends Exception{

    enum ExceptionType {

        CENSUS_FILE_PROBLEM ,
        INVALID_FILE_TYPE ,
        INVALID_FILE_DATA ;
    }
    ExceptionType type;

    public StateCodeAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public StateCodeAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

