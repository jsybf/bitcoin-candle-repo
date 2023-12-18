package gitp.upbitapi.api.timeutil;

import java.time.LocalDateTime;


/**
 * upbit의 candle api가 endTime의 캔들은 포함하지 않는다.
 * 예시로: 10:11:00 ~ 11:25:00까지 1분캔들을 요청하면 11:25의 캔들은 포함되지 않음
 */
public class PeriodSplitter {

    //unit: minute of candle
    public static SplitPeriodIterator split(LocalDateTime startTime, LocalDateTime endTime, int batchSize, int unit) {
        int batchCount = 0;
        //residual: left dateTimes after all available batches
        int residualCount = 0;
        LocalDateTime batchTime = startTime;

        //because batchTime.plusMinutes(~~).isBefore(endtime) doesn't include endtime
        while (!batchTime.plusMinutes((long) batchSize * unit)
                .isAfter(endTime)) {
            batchCount++;
            batchTime = batchTime.plusMinutes((long) batchSize * unit);
        }

        //because batchTime.plusMinutes(~~).isBefore(endtime) doesn't include endtime
        while (!batchTime.plusMinutes(unit)
                .isAfter(endTime)) {
            residualCount++;
            batchTime = batchTime.plusMinutes(unit);
        }

        return new SplitPeriodIterator(batchCount, residualCount, unit, batchSize, startTime.plusMinutes((long) batchSize * unit));
    }

}
