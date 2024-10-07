package fr.insa.soap;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "analyzer")


public class AnalyseChaineWS {
    @WebMethod(operationName = "compare")
    public int analyser(@WebParam(name="chain") String chaine) {
        return chaine.length();
    }

}
