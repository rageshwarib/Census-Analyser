package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCodeAnalyserTests {
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = stateCodeAnalyser.loadIndiaStateCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (IndiaStateCensusException e) { }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(IndiaStateCensusException.class);
            stateCodeAnalyser.loadIndiaStateCensusData(WRONG_CSV_FILE_PATH);
        } catch (IndiaStateCensusException e) {
            Assert.assertEquals(IndiaStateCensusException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
}
