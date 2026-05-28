package mac286.Project;

/**
 * Represents a single daily OHLC bar parsed from a CSV row.
 * Format: Date, Open, High, Low, Close, AdjClose, Volume
 */
public class Bar {
    private String date;
    private float open;
    private float high;
    private float low;
    private float close;
    private float adjClose;
    private long volume;

    public Bar(String line) {
        String[] parts = line.split(",");
        if (parts.length != 7) {
            System.out.println("Error: malformed bar line: " + line);
            return;
        }
        date     = parts[0];
        open     = Float.parseFloat(parts[1]);
        high     = Float.parseFloat(parts[2]);
        low      = Float.parseFloat(parts[3]);
        close    = Float.parseFloat(parts[4]);
        adjClose = Float.parseFloat(parts[5]);
        volume   = Long.parseLong(parts[6]);
    }

    /** Returns the high-low range of this bar. */
    public float range() {
        return high - low;
    }

    public String getDate()             { return date; }
    public void   setDate(String date)  { this.date = date; }

    public float getOpen()              { return open; }
    public void  setOpen(float open)    { this.open = open; }

    public float getHigh()              { return high; }
    public void  setHigh(float high)    { this.high = high; }

    public float getLow()               { return low; }
    public void  setLow(float low)      { this.low = low; }

    public float getClose()             { return close; }
    public void  setClose(float close)  { this.close = close; }

    public float getAdjClose()                  { return adjClose; }
    public void  setAdjClose(float adjClose)    { this.adjClose = adjClose; }

    public long getVolume()             { return volume; }
    public void setVolume(long volume)  { this.volume = volume; }

    @Override
    public String toString() {
        return date + ", " + open + ", " + high + ", " + low + ", "
             + close + ", " + adjClose + ", " + volume;
    }
}
