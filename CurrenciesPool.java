package kambu.rekrutacja;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CurrenciesPool {

//    == constants ==
    public static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/";
    public static final String NBP_TABLES = "tables/a/?format=json";
    public static final String NBP_RATES = "rates/a/";

//    == fields ==
    @Autowired
    private RestCallRepo restCallRepo;
    private final Set<Currency> allCurrencies;

//    == constructor ==
    public CurrenciesPool() {
        log.info("\nPool started");
        allCurrencies = new HashSet<>();
    }

    @PostConstruct
    private void getCurrencyData() {
        restCallRepo.save(new RestCall("NBP api: ".concat(NBP_API_URL).concat(NBP_TABLES)));
        JsonNode rootNode = createJsonNodeFromUrlString(NBP_TABLES);
        if(rootNode == null) return;
        JsonNode foundCurrencies = rootNode.findValues("rates").get(0);
        for (JsonNode foundCurrency : foundCurrencies) {
            allCurrencies.add(createCurrencyFromJson(foundCurrency));
        }
    }

//    == getters ==
    public Currency getCurrency(String code) {
        Currency currency = allCurrencies.stream().filter(curr -> curr.getCode().equals(code.toUpperCase())).findFirst().orElse(null);
        if(currency != null) {
            restCallRepo.save(new RestCall("NBP api: ".concat(CurrenciesPool.NBP_API_URL).concat(CurrenciesPool.NBP_RATES.concat(code))));
            currency.updateCurrencyExchange();
            return currency;
        } else return null;
    }

    public Set<Currency> getAllCurrencies() {
        for (Currency currency : allCurrencies) {
            restCallRepo.save(new RestCall("NBP api: ".concat(CurrenciesPool.NBP_API_URL).concat(CurrenciesPool.NBP_RATES.concat(currency.getCode()))));
            currency.updateCurrencyExchange();
        }
        return allCurrencies;
    }

//    == static or private methods ==
    public static JsonNode createJsonNodeFromUrlString(String urlToConcat) {
        String readData;
        try {
            URL nbpApi = new URL(NBP_API_URL.concat(urlToConcat));
            readData = getUrlContent(nbpApi);
        } catch (IOException e) {
            log.warn("Failed to get data from NBP api");
            e.printStackTrace();
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(readData);
        } catch (JsonProcessingException e) {
            log.warn("Failed to convert read data to json");
            e.printStackTrace();
            return null;
        }
        return rootNode;
    }

    private static String getUrlContent(URL url) throws IOException {
        URLConnection con;
        BufferedReader bufferedReader;
        con = url.openConnection();
        InputStream inputStream;
        try {
            inputStream = con.getInputStream();
        } catch (SSLException e) {
            return null;
        }
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.readLine();
    }

    private Currency createCurrencyFromJson(JsonNode jsonCurrency) {
        String name = jsonCurrency.findValue("currency").toString().replaceAll("\"", "");
        String code = jsonCurrency.findValue("code").toString().replaceAll("\"", "");
        double exchange = jsonCurrency.findValue("mid").doubleValue();
        return new Currency(name, code, exchange);
    }


}
