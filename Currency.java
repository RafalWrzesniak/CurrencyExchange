package kambu.rekrutacja;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


public class Currency {

//    == fields ==
    @Getter private final String name;
    @Getter private final String code;
    @Getter @Setter private double exchangeRate;

//    == constructor ==
    public Currency(String name, String code, double exchange) {
        this.name = name;
        this.code = code;
        this.exchangeRate = exchange;
    }

//    == methods ==
    public double exchangeTo(Currency currencyToExchange, double amount) {
        return amount * (exchangeRate / currencyToExchange.getExchangeRate());
    }

    public void updateCurrencyExchange() {
        JsonNode jsonData = CurrenciesPool.createJsonNodeFromUrlString(CurrenciesPool.NBP_RATES.concat(code));
        if(jsonData != null) {
            exchangeRate = jsonData.findValue("mid").doubleValue();
        }

    }

//    == override methods ==
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return getCode().equals(currency.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", exchange=" + exchangeRate +
                '}';
    }
}
