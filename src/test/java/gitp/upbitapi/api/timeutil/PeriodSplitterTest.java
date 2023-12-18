package gitp.upbitapi.api.timeutil;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PeriodSplitterTest {

    @Test
    void splitTest() throws Exception {
        //given
        LocalDateTime startTime = LocalDateTime.of(2023, 1, 11, 9, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 1, 11, 9, 43, 0);
        SplitPeriodIterator splitIter = PeriodSplitter.split(startTime, endTime, 5, 1);
        while (splitIter.hasNext()) {
            System.out.println(splitIter.next());
        }
    }
}