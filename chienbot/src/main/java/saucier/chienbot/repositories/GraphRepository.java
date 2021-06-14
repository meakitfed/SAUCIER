package saucier.chienbot.repositories;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tictactec.ta.lib.Core;
import org.springframework.stereotype.Repository;
import saucier.chienbot.domain.classes.BinanceCandleStick;
import saucier.chienbot.domain.classes.CandleStickData;
import saucier.chienbot.domain.classes.Rsi;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GraphRepository {

    List<Candlestick> beforeEvents = new ArrayList<>();
    List<CandlestickEvent> events = new ArrayList<>();
    List<BinanceCandleStick> eventsTest = new ArrayList<>();

    public GraphRepository(){
        Rsi rsi = Rsi.builder().overbought(55).oversold(45).timePeriod(20).core(new Core()).build();
        Rsi rsiTest = Rsi.builder().overbought(55).oversold(45).timePeriod(20).core(new Core()).build();
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
        BinanceApiRestClient restClient = factory.newRestClient();
        String symbol = "ADAUSDT";
        List<Candlestick> candlesticks = restClient.getCandlestickBars(symbol, CandlestickInterval.ONE_MINUTE);
        System.out.println(candlesticks);
        beforeEvents.addAll(candlesticks);
        client.onCandlestickEvent(symbol.toLowerCase(), CandlestickInterval.ONE_MINUTE, response -> newCandleStickEvent(rsi, events, response));
        URL resource = getClass().getClassLoader().getResource("static/ADA/ADA-4H/ADAUSDT-4h-2018-12.csv");
        try{
            List<BinanceCandleStick> candle = new CsvToBeanBuilder(new FileReader(resource.toURI().getPath()))
                    .withType(BinanceCandleStick.class)
                    .build()
                    .parse();
            candle.forEach(c -> newCandleStickEventTest(rsiTest, eventsTest, c));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void newCandleStickEventTest(Rsi rsi, List<BinanceCandleStick> events, BinanceCandleStick event){
            System.out.println(event);
            events.add(event);
            rsi.actualise(events.stream().mapToDouble(c -> Double.parseDouble(c.getClose())).toArray());
            System.out.println("Close: " + event.getClose() + "|||| RSI :" + rsi.rsi);
    }

    private static void newCandleStickEvent(Rsi rsi, List<CandlestickEvent> events, CandlestickEvent event){
        if(event.getBarFinal()){
            System.out.println(event);
            events.add(event);
            rsi.actualise(events.stream().mapToDouble(c -> Double.parseDouble(c.getClose())).toArray());
            System.out.println("Close: " + event.getClose() + "|||| RSI :" + rsi.rsi);
        }
    }

    public List<CandleStickData> getEvents(){
        List<CandleStickData> candleStickData = beforeEvents.stream()
                                                      .map(event -> new CandleStickData(event))
                                                      .collect(Collectors.toList());
        candleStickData.addAll(events.stream()
                                     .map(event -> new CandleStickData(event))
                                     .collect(Collectors.toList()));
        return candleStickData;
    }

    public List<CandleStickData> getTestEvents(){
        return eventsTest.stream().map(event -> new CandleStickData(event)).collect(Collectors.toList());
    }

}
