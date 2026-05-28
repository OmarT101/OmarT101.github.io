package mac286.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Loads historical OHLC data for a single symbol and runs a 20-day
 * breakout backtesting strategy, producing a list of simulated trades.
 *
 * Strategy rules:
 *   Long  — today is a 20-day low, opens below prior low, closes above
 *            prior close, and prior day was bearish.
 *   Short — today is a 20-day high, opens above prior high, closes below
 *            prior close, and prior day was bullish.
 *
 * Entry is at next-day open. Stop-loss and profit-target are calculated
 * using the configurable risk factor.
 */
public class SymbolTester {
    private String symbol;
    private String dataPath;
    private float  riskFactor;

    private Vector<Bar>   mData;
    private Vector<Trade> mTrades;
    private boolean dataLoaded = false;

    public SymbolTester(String symbol, String dataPath, float riskFactor) {
        this.symbol     = symbol;
        this.dataPath   = dataPath;
        this.riskFactor = riskFactor;
        mData   = new Vector<>(3000);
        mTrades = new Vector<>(200);
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }

    /** Reads the CSV file and populates mData with Bar objects. */
    public void loadData() {
        String fileName = dataPath + symbol + "_Daily.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                mData.add(new Bar(line));
            }
            dataLoaded = true;
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            dataLoaded = false;
        }
    }

    /** Returns true if bar at {@code ind} is the lowest low over the past {@code days} bars. */
    private boolean isXDayLow(int ind, int days) {
        for (int i = ind - 1; i > ind - days; i--) {
            if (mData.get(i).getLow() < mData.get(ind).getLow())
                return false;
        }
        return true;
    }

    /** Returns true if bar at {@code ind} is the highest high over the past {@code days} bars. */
    private boolean isXDayHigh(int ind, int days) {
        for (int i = ind - 1; i > ind - days; i--) {
            if (mData.get(i).getHigh() > mData.get(ind).getHigh())
                return false;
        }
        return true;
    }

    /**
     * Walks forward from {@code startInd} to determine the trade outcome,
     * accounting for gap opens past stop-loss or target.
     */
    private void resolveOutcome(Trade trade, int startInd) {
        for (int i = startInd; i < mData.size(); i++) {
            Bar bar = mData.get(i);

            if (trade.getDir() == Direction.LONG) {
                if (bar.getHigh() > trade.getTarget()) {
                    float exitPrice = bar.getOpen() > trade.getTarget()
                            ? bar.getOpen() : trade.getTarget();
                    trade.close(bar.getDate(), exitPrice, i - startInd);
                    return;
                } else if (bar.getLow() < trade.getStopLoss()) {
                    float exitPrice = bar.getOpen() < trade.getStopLoss()
                            ? bar.getOpen() : trade.getStopLoss();
                    trade.close(bar.getDate(), exitPrice, i - startInd);
                    return;
                }
            } else { // SHORT
                if (bar.getLow() <= trade.getTarget()) {
                    float exitPrice = bar.getOpen() < trade.getTarget()
                            ? bar.getOpen() : trade.getTarget();
                    trade.close(bar.getDate(), exitPrice, i - startInd);
                    return;
                } else if (bar.getHigh() >= trade.getStopLoss()) {
                    float exitPrice = bar.getOpen() > trade.getStopLoss()
                            ? bar.getOpen() : trade.getStopLoss();
                    trade.close(bar.getDate(), exitPrice, i - startInd);
                    return;
                }
            }
        }
        // Trade still open at end of data — close at last bar's close
        Bar last = mData.get(mData.size() - 1);
        trade.close(last.getDate(), last.getClose(), mData.size() - 1 - startInd);
    }

    /**
     * Runs the backtest. Returns false if data cannot be loaded.
     * Results are stored in mTrades and accessible via getTrades().
     */
    public boolean test() {
        if (!dataLoaded) {
            loadData();
            if (!dataLoaded) {
                System.out.println("Backtest aborted: data not loaded.");
                return false;
            }
        }

        for (int i = 20; i < mData.size() - 1; i++) {
            Bar today = mData.get(i);
            Bar prev  = mData.get(i - 1);
            Bar next  = mData.get(i + 1);

            // Long signal: 20-day low with bearish-to-bullish reversal confirmation
            if (isXDayLow(i, 20)
                    && today.getOpen()  < prev.getLow()
                    && today.getClose() > prev.getClose()
                    && prev.getClose()  < prev.getOpen()) {

                float entry    = next.getOpen();
                float stopLoss = today.getLow() - 0.01f;
                float risk     = entry - stopLoss;
                float target   = entry + riskFactor * risk;

                Trade trade = new Trade();
                trade.open(symbol, next.getDate(), entry, stopLoss, target, Direction.LONG);
                resolveOutcome(trade, i + 1);
                mTrades.add(trade);

            // Short signal: 20-day high with bullish-to-bearish reversal confirmation
            } else if (isXDayHigh(i, 20)
                    && today.getOpen()  > prev.getHigh()
                    && today.getClose() < prev.getClose()
                    && prev.getClose()  > prev.getOpen()) {

                float entry    = next.getOpen();
                float stopLoss = today.getHigh() + 0.01f;
                float risk     = stopLoss - entry;
                float target   = entry - riskFactor * risk;

                Trade trade = new Trade();
                trade.open(symbol, next.getDate(), entry, stopLoss, target, Direction.SHORT);
                resolveOutcome(trade, i + 1);
                mTrades.add(trade);
            }
        }

        return true;
    }
}
