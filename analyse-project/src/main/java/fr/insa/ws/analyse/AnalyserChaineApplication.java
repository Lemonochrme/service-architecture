package fr.insa.ws.analyse;

import fr.insa.ws.analyse.*;
import java.net.MalformedURLException;
import javax.xml.ws.Endpoint;

public class AnalyserChaineApplication {
    
    public static String host="localhost";
    public static short port=8080;


    public void demarrerService() {
        String url = "http://"+host+":"+port+"/analyser";

        Endpoint.publish(url, new AnalyseChaineWS());
    }



}
