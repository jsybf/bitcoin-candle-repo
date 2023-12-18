package gitp.upbitapi;

import gitp.upbitapi.api.UpbitCandleApiClient;
import gitp.upbitapi.api.timeutil.SplitPeriodIterator;
import gitp.upbitapi.domain.Candle;
import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.dto.CandleDto;
import gitp.upbitapi.repository.CandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandleService {
    private final UpbitCandleApiClient candleApiClient;
    private final CandleRepository candleRepository;

    @Transactional
    public long getCandleWithinPeriod(SplitPeriodIterator periodIterator, MarketCode marketCode, int unit) {
        List<List<CandleDto>> responseCandleList = candleApiClient.getMinuteCandle(periodIterator, marketCode, unit)
                .collectList()
                .block();

        responseCandleList.stream()
                .flatMap(Collection::stream)
                .map(Candle::new)
                .forEach(candleRepository::save);

        return responseCandleList.stream()
                .mapToLong(Collection::size)
                .sum();
    }
}
