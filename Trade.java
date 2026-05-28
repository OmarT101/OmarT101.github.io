package mac286.Project;

/**
 * Represents a single simulated trade with full lifecycle tracking:
 * entry/exit dates, prices, direction, stop-loss, target, and outcome.
 */
public class Trade {
    private String symbol;
    private String entryDate;
    private String exitDate;
    private float  entryPrice;
    private float  exitPrice;
    private float  stopLoss;
    private float  target;
    private Direction dir;
    private int    holdingPeriod;
    public  boolean isOn;

    public Trade() {
        symbol        = "";
        entryDate     = "";
        entryPrice    = 0;
        stopLoss      = 0;
        target        = 0;
        holdingPeriod = 0;
        isOn          = false;
        dir           = Direction.NONE;
    }

    /** Opens the trade with all entry parameters. */
    public void open(String symbol, String entryDate, float price,
                     float stopLoss, float target, Direction dir) {
        this.symbol     = symbol;
        this.entryDate  = entryDate;
        this.entryPrice = price;
        this.stopLoss   = stopLoss;
        this.target     = target;
        this.dir        = dir;
        this.isOn       = true;
        this.holdingPeriod = 0;
    }

    /** Closes the trade at the given exit price and date. */
    public void close(String exitDate, float exitPrice, int holdingPeriod) {
        this.exitDate      = exitDate;
        this.exitPrice     = exitPrice;
        this.holdingPeriod = holdingPeriod;
        this.isOn          = false;
    }

    /**
     * Returns profit/loss as a percentage of entry price.
     * Positive = win, negative = loss.
     */
    public float percentPL() {
        if (dir == Direction.LONG) {
            return ((exitPrice - entryPrice) / entryPrice) * 100;
        } else if (dir == Direction.SHORT) {
            return ((entryPrice - exitPrice) / entryPrice) * 100;
        }
        System.out.println("Warning: trade has no direction — percentPL() returning 0.");
        return 0;
    }

    @Override
    public String toString() {
        return symbol + ", " + entryDate + ", " + entryPrice + ", "
             + stopLoss + ", " + target + ", " + dir + ", "
             + exitDate + ", " + exitPrice + ", " + holdingPeriod;
    }

    public String    getSymbol()                    { return symbol; }
    public String    getEntryDate()                 { return entryDate; }
    public void      setEntryDate(String entryDate) { this.entryDate = entryDate; }
    public String    getExitDate()                  { return exitDate; }
    public void      setExitDate(String exitDate)   { this.exitDate = exitDate; }
    public float     getEntryPrice()                { return entryPrice; }
    public void      setEntryPrice(float p)         { this.entryPrice = p; }
    public float     getExitPrice()                 { return exitPrice; }
    public void      setExitPrice(float p)          { this.exitPrice = p; }
    public float     getStopLoss()                  { return stopLoss; }
    public void      setStopLoss(float sl)          { this.stopLoss = sl; }
    public float     getTarget()                    { return target; }
    public void      setTarget(float t)             { this.target = t; }
    public Direction getDir()                       { return dir; }
    public void      setDir(Direction dir)          { this.dir = dir; }
    public int       getHoldingPeriod()             { return holdingPeriod; }
    public void      setHoldingPeriod(int hp)       { this.holdingPeriod = hp; }
}
