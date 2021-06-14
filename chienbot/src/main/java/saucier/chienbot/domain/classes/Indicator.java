package saucier.chienbot.domain.classes;

import java.util.List;

public interface Indicator {
    boolean shouldBuy();
    boolean shouldSell();
    void actualise(double[] closePrices);
}
