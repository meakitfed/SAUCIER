package saucier.chienbot.domain.classes;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

@Getter
public class BinanceCandleStick {

    @CsvBindByPosition(position = 0)
    private String date;

    @CsvBindByPosition(position = 1)
    private String open;

    @CsvBindByPosition(position = 2)
    private String high;

    @CsvBindByPosition(position = 3)
    private String low;

    @CsvBindByPosition(position = 4)
    private String close;
}
