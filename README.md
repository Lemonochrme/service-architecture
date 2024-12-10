# service-architecture
Course Exercice : Application to help others

```bash
mvn compile

mvn spring-boot:run
```

## Check `SOAP` Requests

Please install the following application for ease of use SoapUI:
```bash
flatpak install flathub org.soapui.SoapUI
```
Then in the application create a new project with the endpoint of your choice, one might be `http://localhost:8080/ws/countries.wsdl`. Then modify the given request with the corresponding Country if you use the simple example given by SpringBoot.

## Check `REST` Requests

Use this time the HTTPie application, it can be either in fully CLI or with a great UI, you can install it with flathub using this command:
```bash
flatpak install flathub io.httpie.Httpie
```
Just run the command `http POST localhost:8080 hello=world` to test the REST api.