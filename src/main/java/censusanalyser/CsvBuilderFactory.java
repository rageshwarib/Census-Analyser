package censusanalyser;


import com.BridgeLabz.CsvBuilder;

public class CsvBuilderFactory {
    public static CsvBuilder getCsvBuilder(){
        return new CsvBuilder();
    }
}
