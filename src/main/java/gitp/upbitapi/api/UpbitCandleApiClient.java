package gitp.upbitapi.api;

import gitp.upbitapi.api.timeutil.SplitPeriodIterator;
import gitp.upbitapi.api.timeutil.UpBitCandleRequestData;
import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.dto.CandleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UpbitCandleApiClient {

    private final String upBitBaseUrl = "https://api.upbit.com/v1";

    private final WebClient webClient;

    public UpbitCandleApiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl(upBitBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * TODO: available for all time-type of candle
     */
    public Mono<List<CandleDto>> getMinuteCandle(MarketCode marketCode, int unit, int count) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/candles/minutes/{unit}")
                                .queryParam("market", marketCode.getLabel())
                                .queryParam("count", count)
                                .build(unit)
                )
                .retrieve()
                .bodyToMono(CandleDto[].class)
                .map(Arrays::asList);
    }

    public Flux<List<CandleDto>> getMinuteCandle(SplitPeriodIterator periodIterator, MarketCode marketCode, int unit) {
        List<Mono<List<CandleDto>>> responseMonoList = new ArrayList<>();

        while (periodIterator.hasNext()) {
            UpBitCandleRequestData candleRequestData = periodIterator.next();
            log.debug("requestData: {}", candleRequestData);
            Mono<List<CandleDto>> candleWebClient = webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/candles/minutes/{unit}")
                                    .queryParam("market", marketCode.getLabel())
                                    .queryParam("count", candleRequestData.getBatchSize())
                                    .queryParam("to", candleRequestData.getLastDateTime()
                                            .toString())
                                    .build(unit)
                    )
                    .retrieve()
                    .bodyToMono(CandleDto[].class)
                    .map(Arrays::asList);

            responseMonoList.add(candleWebClient);
        }

        return Flux.concat(responseMonoList)
                .delayElements(Duration.ofMillis(100));
    }
}

