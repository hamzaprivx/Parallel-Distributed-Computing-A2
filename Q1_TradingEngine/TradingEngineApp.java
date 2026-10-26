
package Q1_TradingEngine;

public class TradingEngineApp {
    public static void main(String[] args) {
        TradeEvent event1 = new FIXOrderEvent(
            "FIX-Gateway",
            System.currentTimeMillis(),
            1000.0,
            "ORD101"
        );

        TradeEvent event2 = new ReutersQuoteEvent(
            "Reuters-Gateway",
            System.currentTimeMillis(),
            101.0,
            100.5,
            101.5
        );

        event1.dispatch();

        System.out.println();

        event2.dispatch();
    }
}
