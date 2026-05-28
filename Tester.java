package mac286.Project;

import java.util.Vector;

/**
 * Entry point for the backtesting engine.
 * Update DATA_PATH to point to your local CSV data folder before running.
 */
public class Tester {

    // Update this path to where your CSV files are stored
    private static final String DATA_PATH   = "/Users/luismora/eclipse-workspace/mac286/Data/";
    private static final String SYMBOL      = "AAPL";
    private static final float  RISK_FACTOR = 2.0f;

    public static void main(String[] args) {
        SymbolTester tester = new SymbolTester(SYMBOL, DATA_PATH, RISK_FACTOR);
        tester.test();

        Vector<Trade> trades = tester.getTrades();
        System.out.println("Total trades: " + trades.size());
        System.out.println("Symbol, EntryDate, EntryPrice, StopLoss, Target, Direction, ExitDate, ExitPrice, HoldingDays");
        for (Trade t : trades) {
            System.out.println(t);
        }
    }
}
