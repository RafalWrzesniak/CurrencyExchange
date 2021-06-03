# CurrencyExchange

### Pliki dockerowe oraz .jar dostępne w zakładce Releases

### Dostępne wywołania:
#### - Pokazuje wszystkie dostęne waluty wraz z aktualnym kursem (dostępne są wszystkie udostęnione przez api NBP):
curl localhost:8080/currencies
#### - Pokazuje konkretną walutę wraz z aktualnym kursem:
curl localhost:8080/currencies/{code}  
Przykłady:  
curl localhost:8080/currencies/USD  
curl localhost:8080/currencies/eur
#### - Przelicza podaną ilość jednej waluty na drugą i pokazuje otrzymany wynik:
curl localhost:8080/exchange/from={codeFrom}/to={codeTo}/amount={amount}  
Przykłady:  
curl localhost:8080/exchange/from=usd/to=eur/amount=100  
curl localhost:8080/exchange/from=cny/to=usd/amount=20000
#### - Pokazuje wszystkie wywołania do tego programu i do api NBP
curl localhost:8080/db
