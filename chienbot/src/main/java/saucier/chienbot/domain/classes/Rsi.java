package saucier.chienbot.domain.classes;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Rsi implements Indicator {

    public int oversold;
    public int overbought;
    public int timePeriod;
    public double rsi;
    public Core core;

    public Rsi(){
        rsi = 50;
    }
    @Override
    public boolean shouldBuy() {
        return oversold > rsi;
    }

    @Override
    public boolean shouldSell() {
        return overbought < rsi;
    }

    @Override
    public void actualise(double[] closePrices) {

        if(closePrices.length > timePeriod){
            MInteger outBegIdx = new MInteger();
            MInteger outNbElement = new MInteger();
            double[] outReal = new double[closePrices.length];
            int startIdx = 0;
            int endIdx = closePrices.length - 1;
            core.rsi(startIdx, endIdx, closePrices, timePeriod, outBegIdx, outNbElement, outReal);
            rsi = outReal[outReal.length - timePeriod - 1];
        }
    }
}
