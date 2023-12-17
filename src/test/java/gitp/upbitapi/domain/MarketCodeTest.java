package gitp.upbitapi.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MarketCodeTest {

    @Test
    void ofTest() throws Exception {
        Assertions.assertThat(MarketCode.of("KRW-BTC"))
                .isEqualTo(MarketCode.BIT_COIN);
        Assertions.assertThat(MarketCode.of("LALA-BTC"))
                .isEqualTo(MarketCode.NONE);
    }
}