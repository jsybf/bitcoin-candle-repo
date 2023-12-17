package gitp.upbitapi.api;

import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.dto.CandleDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

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


}
