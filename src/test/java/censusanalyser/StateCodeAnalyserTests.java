package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCodeAnalyserTests {
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCode.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/StateCodeInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";

    StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = stateCodeAnalyser.loadIndiaStateCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (StateCodeAnalyserException e) { }
    }
    @Test
    public void givenIndiaStateData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeAnalyserException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_PATH);
        } catch (StateCodeAnalyserException e) {
            Assert.assertEquals(StateCodeAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeAnalyserException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_TYPE);
        } catch (StateCodeAnalyserException e) {
            Assert.assertEquals(StateCodeAnalyserException.ExceptionType.INVALID_FILE_TYPE,e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeData_WithWrongDelimiter_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeAnalyserException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (StateCodeAnalyserException e) {
            Assert.assertEquals(StateCodeAnalyserException.ExceptionType.INVALID_FILE_DATA,e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeData_WithWrongHeader_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeAnalyserException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_HEADER);
        } catch (StateCodeAnalyserException e) {
            Assert.assertEquals(StateCodeAnalyserException.ExceptionType.INVALID_FILE_HEADER,e.type);
        }
    }
}
