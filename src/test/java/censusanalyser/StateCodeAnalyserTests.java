package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCodeAnalyserTests {
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCode.txt";

    StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = stateCodeAnalyser.loadIndiaStateCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (StateCodeException e) { }
    }
    @Test
    public void givenIndiaStateData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_PATH);
        } catch (StateCodeException e) {
            Assert.assertEquals(StateCodeException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(StateCodeException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_TYPE);
        } catch (StateCodeException e) {
            Assert.assertEquals(StateCodeException.ExceptionType.INVALID_FILE_TYPE,e.type);
        }
    }
}
