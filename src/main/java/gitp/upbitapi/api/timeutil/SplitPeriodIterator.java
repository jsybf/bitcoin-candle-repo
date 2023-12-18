package gitp.upbitapi.api.timeutil;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * upbit의 candle api가 endTime의 캔들은 포함하지 않는다.
 * 예시로: 10:11:00 ~ 11:25:00까지 1분캔들을 요청하면 11:25의 캔들은 포함되지 않음
 * unit: 요청하는 캔들의 분(1, 3, 5, 15, 10, 30, 60, 240)등을 지정한다
 */
public class SplitPeriodIterator implements Iterator<UpBitCandleRequestData> {
    private final int numOfBatch;
    //residual: left dateTimes after all available batches
    @Getter
    private final int residualSize;
    private final int unit;
    private final int batchSize;
    private boolean ifResidualExecuted = false;
    private int batchCounter = 0;
    private LocalDateTime dateTimePointer;


    public SplitPeriodIterator(int numOfBatch, int residualSize, int unit, int batchSize, LocalDateTime startTime) {
        this.numOfBatch = numOfBatch;
        this.residualSize = residualSize;
        this.unit = unit;
        this.batchSize = batchSize;
        this.dateTimePointer = startTime.minusMinutes((long) unit * batchSize);
    }

    @Override
    public boolean hasNext() {
        if (batchCounter < numOfBatch) {
            return true;
        } else if (!ifResidualExecuted && residualSize != 0) {
            return true;
        }

        return false;
    }

    @Override
    public UpBitCandleRequestData next() {
        if (batchCounter < numOfBatch) {
            batchCounter++;
            dateTimePointer = dateTimePointer.plusMinutes((long) batchSize * unit);
            return new UpBitCandleRequestData(batchSize, dateTimePointer);
        } else {
            ifResidualExecuted = true;
            for (int i = 0; i < residualSize; i++) {
                dateTimePointer = dateTimePointer.plusMinutes(unit);
            }
            return new UpBitCandleRequestData(residualSize, dateTimePointer);
        }
    }

    //unsupported
    @Override
    public void remove() {
        Iterator.super.remove();
    }

    //unsupported
    @Override
    public void forEachRemaining(Consumer<? super UpBitCandleRequestData> action) {
        Iterator.super.forEachRemaining(action);
    }
}
