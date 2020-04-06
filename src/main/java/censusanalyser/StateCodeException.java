package censusanalyser;

public class StateCodeException extends Exception{

    enum ExceptionType {

        CENSUS_FILE_PROBLEM ,
        INVALID_FILE_TYPE ;
    }
    ExceptionType type;

    public StateCodeException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public StateCodeException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

