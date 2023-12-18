package gitp.upbitapi.api.timeutil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class UpBitCandleRequestData {
    private final int batchSize;
    private final LocalDateTime lastDateTime;

}
