package gitp.upbitapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class CandleDtoTest {
    @Test
    void candleDtoTest() throws Exception {
        String sampleJson = """
                { "market": "KRW-BTC",
                "candle_date_time_utc": "2018-04-18T10:16:00",
                "candle_date_time_kst": "2018-04-18T19:16:00",
                "opening_price": 8615000,
                "high_price": 8618000,
                "low_price": 8611000,
                "trade_price": 8616000,
                "timestamp": 1524046594584,
                "candle_acc_trade_price": 60018891.90054,
                "candle_acc_trade_volume": 6.96780929,
                "unit": 1}
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        CandleDto candleDto = objectMapper.readValue(sampleJson, CandleDto.class);

        System.out.println(candleDto.getMarketCode());
        System.out.println(candleDto.getTimeStamp());
    }
}