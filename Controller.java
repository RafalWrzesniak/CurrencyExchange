package kambu.rekrutacja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class Controller {

    @Autowired
    private CurrenciesPool currenciesPool;
    @Autowired
    private RestCallRepo restCallRepo;

    @GetMapping("/currencies")
    Set<Currency> getAllCurrencies() {
        restCallRepo.save(new RestCall("GetMapping: http://localhost:8080/currencies"));
        return currenciesPool.getAllCurrencies();
    }

    @GetMapping("/currencies/{code}")
    Currency getOneCurrency(@PathVariable String code) {
        restCallRepo.save(new RestCall("GetMapping: http://localhost:8080/currencies/".concat(code)));
        return currenciesPool.getCurrency(code);
    }

    @GetMapping("/exchange/from={codeFrom}/to={codeTo}/amount={amount}")
    Double exchange(@PathVariable String codeFrom, @PathVariable String codeTo, @PathVariable double amount) {
        restCallRepo.save(new RestCall("GetMapping: http://localhost:8080/exchange/from="
                .concat(codeFrom).concat("/to=").concat(codeTo).concat("/amount=").concat(String.valueOf(amount))
        ));
        return currenciesPool.getCurrency(codeFrom).exchangeTo(currenciesPool.getCurrency(codeTo), amount);
    }

    @GetMapping("/db")
    List<RestCall> getCalls() {
        restCallRepo.save(new RestCall("GetMapping: http://localhost:8080/db/"));
        List<RestCall> calls = new ArrayList<>();
        for(RestCall call : restCallRepo.findAll()) {
            calls.add(call);
        }
        return calls;
    }


}
