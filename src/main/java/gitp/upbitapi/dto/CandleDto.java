package gitp.upbitapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CandleDto {

    @JsonProperty("market")
    private String marketCode;

    @JsonProperty("candle_date_time_utc")
    private String candleDateTimeUtc;

    @JsonProperty("candle_date_time_kst")
    private String candleDateTimeKst;

    @JsonProperty("opening_price")
    private Double openingPrice;

    @JsonProperty("high_price")
    private Double highPrice;

    @JsonProperty("low_price")
    private Double lowPrice;

    @JsonProperty("trade_price")
    private Double tradePrice;

    @JsonProperty("timestamp")
    private Long timeStamp;

    @JsonProperty("candle_acc_trade_price")
    private Double accumulatedTradePrice;

    @JsonProperty("candle_acc_trade_volume")
    private Double accumulatedTradeVolume;

    @JsonProperty("unit")
    private Integer unit;
}
