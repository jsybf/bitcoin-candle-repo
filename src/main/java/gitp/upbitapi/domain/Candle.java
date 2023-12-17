package gitp.upbitapi.domain;

import gitp.upbitapi.dto.CandleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * TODO: refactor type of marketCode, CandleDateTimeUtc
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString //danger when bothway relationship
public class Candle {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private MarketCode marketCode;

    private LocalDateTime candleDateTimeUtc;

    private Double openingPrice;

    private Double highPrice;

    private Double lowPrice;

    private Double tradePrice;

    private Long timeStamp;

    private Double accumulatedTradePrice;

    private Double accumulatedTradeVolume;

    private Integer unit;

    public Candle(CandleDto candleDto) {
        this.marketCode = MarketCode.of(candleDto.getMarketCode());
        this.candleDateTimeUtc = LocalDateTime.parse(candleDto.getCandleDateTimeUtc());
        this.openingPrice = candleDto.getOpeningPrice();
        this.highPrice = candleDto.getHighPrice();
        this.lowPrice = candleDto.getLowPrice();
        this.tradePrice = candleDto.getTradePrice();
        this.timeStamp = candleDto.getTimeStamp();
        this.accumulatedTradePrice = candleDto.getAccumulatedTradePrice();
        this.accumulatedTradeVolume = candleDto.getAccumulatedTradeVolume();
    }
}
