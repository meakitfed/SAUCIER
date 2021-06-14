package saucier.chienbot.domain.classes;

import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.Candlestick;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CandleStickData {

    public double x;
    public double[] y;

    public CandleStickData(CandlestickEvent event){
        x = event.getCloseTime();
        y = new double[]{Double.parseDouble(event.getOpen()), Double.parseDouble(event.getHigh()), Double.parseDouble(
                event.getLow()), Double.parseDouble(event.getClose())};
    }

    public CandleStickData(BinanceCandleStick event){
        x = Double.parseDouble(event.getDate());
        y = new double[]{Double.parseDouble(event.getOpen()), Double.parseDouble(event.getHigh()), Double.parseDouble(
                event.getLow()), Double.parseDouble(event.getClose())};
    }

    public CandleStickData(Candlestick event){
        x = event.getOpenTime();
        y = new double[]{Double.parseDouble(event.getOpen()), Double.parseDouble(event.getHigh()), Double.parseDouble(
                event.getLow()), Double.parseDouble(event.getClose())};
    }
}
