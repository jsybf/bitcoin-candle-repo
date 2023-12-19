package gitp.upbitapi.service;

import gitp.upbitapi.api.timeutil.PeriodSplitter;
import gitp.upbitapi.api.timeutil.SplitPeriodIterator;
import gitp.upbitapi.domain.Candle;
import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.repository.CandleRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@Slf4j
class CandleServiceTest {
    @Autowired
    private CandleService candleService;
    @Autowired
    private CandleRepository candleRepository;

    @Test
    void CandleBatchTest() throws Exception {
        //given
        LocalDateTime startTime = LocalDateTime.of(2022, 10, 1, 2, 23, 00);
        LocalDateTime endTime = LocalDateTime.of(2022, 10, 1, 3, 34, 00);
        SplitPeriodIterator splitIter = PeriodSplitter.split(startTime, endTime, 15, 1);

        //when
        candleService.getCandleWithinPeriod(splitIter, MarketCode.BIT_COIN, 1);
        candleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Candle::getCandleDateTimeUtc))
                .forEach(candle -> log.info("date: {}", candle.getCandleDateTimeUtc()
                        .toString()));

        //then
        List<Candle> candleListSortedByDateTime = candleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Candle::getCandleDateTimeUtc))
                .toList();

        boolean discontinuousFlag = false;
        for (int i = 0; i < candleListSortedByDateTime.size() - 1; i++) {
            LocalDateTime currentDateTime = candleListSortedByDateTime.get(i)
                    .getCandleDateTimeUtc();
            LocalDateTime nextDateTime = candleListSortedByDateTime.get(i + 1)
                    .getCandleDateTimeUtc();
            if (!currentDateTime.plusMinutes(1L)
                    .equals(nextDateTime)) {
                discontinuousFlag = true;
                log.error("i: {} \n i+1: {}", currentDateTime.toString(), nextDateTime.toString());
            }
        }

        Assertions.assertThat(discontinuousFlag)
                .isEqualTo(false);

    }
}