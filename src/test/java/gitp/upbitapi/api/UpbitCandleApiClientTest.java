package gitp.upbitapi.api;

import gitp.upbitapi.api.timeutil.PeriodSplitter;
import gitp.upbitapi.api.timeutil.SplitPeriodIterator;
import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.dto.CandleDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@Slf4j
class UpbitCandleApiClientTest {
    @Autowired
    private UpbitCandleApiClient apiClient;

    @Test
    void getMinuteCandleTest() throws Exception {
        //given
        Mono<List<CandleDto>> responseMono = apiClient.getMinuteCandle(MarketCode.BIT_COIN, 1, 10);
        //when
        List<CandleDto> block = responseMono.block();

        //then
        block.forEach(candleDto -> System.out.println(candleDto.toString()));
    }

    @Test
    void getMinuteCandleTestWithinDuration() throws Exception {
        //given
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 23, 7, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 23, 8, 52, 30);

        SplitPeriodIterator splitIter = PeriodSplitter.split(startTime, endTime, 10, 1);

        //when
        List<List<CandleDto>> responseCandles = apiClient.getMinuteCandle(splitIter, MarketCode.BIT_COIN, 1)
                .collectList()
                .block();

        responseCandles.stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(CandleDto::getCandleDateTimeUtc))
                .forEach(candleDto -> System.out.println(candleDto.getCandleDateTimeUtc()));


    }
}