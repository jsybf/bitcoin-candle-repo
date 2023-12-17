package gitp.upbitapi.api;

import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.dto.CandleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
class UpbitCandleApiClientTest {
    @Autowired private UpbitCandleApiClient apiClient;

    @Test
    void getMinuteCandleTest() throws Exception {
        //given
        Mono<List<CandleDto>> responseMono = apiClient.getMinuteCandle(MarketCode.BIT_COIN, 1, 10);
        //when
        List<CandleDto> block = responseMono.block();

        //then
        block.forEach(candleDto -> System.out.println(candleDto.toString()));
    }
}