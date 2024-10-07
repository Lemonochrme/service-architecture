package fr.insa.soap;

import java.net.MalformedURLException;
import javax.xml.ws.Endpoint;

public class AnalyseChaineApplication {
    
    public static String host="localhost";
    public static short port=8080;

    public void demarrerService() {
        String url = "http://"+host+":"+port+"/analyser";
        Endpoint.publish(url, new AnalyseChaineWS());
    }
    
    public static void main(String[] args) {
        AnalyseChaineApplication app = new AnalyseChaineApplication();
        app.demarrerService();
    }
}
