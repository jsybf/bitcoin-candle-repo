package gitp.upbitapi;

import gitp.upbitapi.api.timeutil.PeriodSplitter;
import gitp.upbitapi.api.timeutil.SplitPeriodIterator;
import gitp.upbitapi.domain.MarketCode;
import gitp.upbitapi.service.CandleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class UpbitapiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UpbitapiApplication.class, args);
        CandleService candleServiceBean = applicationContext.getBean(CandleService.class);

        LocalDateTime startTime = LocalDateTime.of(2023, 10, 1, 13, 11, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 3, 15, 43, 0);
        SplitPeriodIterator periodIterator = PeriodSplitter.split(startTime, endTime, 100, 1);
        candleServiceBean.getCandleWithinPeriod(periodIterator, MarketCode.BIT_COIN, 1);
    }

}
