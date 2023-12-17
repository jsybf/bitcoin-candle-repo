package gitp.upbitapi.domain;

public enum MarketCode {
    BIT_COIN("KRW-BTC"), RIPPLE("XRP-BTC"), NONE("none");

    private final String label;

    MarketCode(String code) {
        this.label = code;
    }

    public String getLabel() {
        return this.label;
    }

    public static MarketCode of(String marketCode) {
        for (MarketCode code : values()) {
            if (code.label.equals(marketCode)) {
                return code;
            }
        }
        return NONE;
    }

}
