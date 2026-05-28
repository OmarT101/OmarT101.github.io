# Omar Torres – Portfolio Projects

**Computer Science student** pursuing a B.A., building hands-on projects in software development.

🌍 **Live Site:** [omart101.github.io](https://omart101.github.io/)

---

## Projects

### 📈 Java Backtesting Engine
A modular Java backtesting sandbox that processes historical daily OHLC data and simulates a 20-day breakout trading strategy.

**Key features:**
- Parses daily OHLC CSV rows into encapsulated `Bar` objects
- Identifies long/short signals using 20-day highs/lows with multi-step confirmation
- Gap-aware stop-loss and profit-target logic at next-day open
- Tracks full trade lifecycle: entry/exit dates, prices, direction, and holding period
- Modular OOP design (`Bar`, `Trade`, `SymbolTester`)
- Configurable risk factor for experimenting with strategy behavior

**How to run:**
1. Open the project in Eclipse or VS Code (with Java extension)
2. Place your `SYMBOL_Daily.csv` file in the `java-backtester/data/` folder
3. Update `DATA_PATH` in `Tester.java` to point to that folder
4. Run `Tester.java`

CSV format expected: `Date, Open, High, Low, Close, AdjClose, Volume`

---

### 🌐 Interactive Website (HTML/CSS/JavaScript)
A responsive multi-section portfolio site built for **CSCI 355 – Internet & Web Technologies**.

**Key features:**
- Tab-based navigation using DOM manipulation
- Live window size display (updates on resize)
- Dynamic browser/navigator/screen/location info
- Password validation with real-time feedback
- Clean semantic HTML5 structure, separated CSS and JS

**How to open:**
- Clone the repo and open `website/index.html` in any browser, or visit the live demo above.

---

## Technologies Used

| Area | Tools |
|---|---|
| Backend / Logic | Java, OOP, File I/O, CSV parsing |
| Frontend | HTML5, CSS3, Vanilla JavaScript |
| Tools | Git, GitHub, Eclipse, VS Code |

---

## Repository Structure

```
omar-portfolio/
│
├── README.md
│
├── website/
│   ├── index.html
│   ├── style.css
│   └── script.js
│
└── java-backtester/
    ├── src/
    │   └── mac286/Project/
    │       ├── Direction.java
    │       ├── Bar.java
    │       ├── Trade.java
    │       ├── SymbolTester.java
    │       └── Tester.java
    └── data/
        └── AAPL_Daily.csv
```

---

## Contact

📧 [omarstudent.10@gmail.com](mailto:omarstudent.10@gmail.com)  
🐙 [github.com/omart101](https://github.com/omart101)
