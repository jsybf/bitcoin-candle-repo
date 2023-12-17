package gitp.upbitapi.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import gitp.upbitapi.dto.CandleDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CandleTest {
    @Autowired
    private EntityManager em;

    @Test
    void constructFromDto() throws Exception {
        //given
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

        //when
        Candle candle = new Candle(candleDto);
        System.out.println(candle.getCandleDateTimeUtc());
    }

    @Transactional
    @Test
    void ddlTest() throws Exception {
        //given
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

        //when
        Candle candle = new Candle(candleDto);

        //then
        em.persist(candle);
        em.flush();
    }

}