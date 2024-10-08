package fr.insa.soap.wsdltojava;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.4.2
 * 2024-10-07T12:12:57.989+02:00
 * Generated source version: 3.4.2
 *
 */
@WebServiceClient(name = "analyzer",
                  wsdlLocation = "file:/home/yoboujon/Documents/GEI/test/AnalyseProject/src/main/resources/wsdl/analyzer.wsdl",
                  targetNamespace = "http://soap.insa.fr/")
public class Analyzer extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://soap.insa.fr/", "analyzer");
    public final static QName AnalyseChaineWSPort = new QName("http://soap.insa.fr/", "AnalyseChaineWSPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/yoboujon/Documents/GEI/test/AnalyseProject/src/main/resources/wsdl/analyzer.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Analyzer.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/home/yoboujon/Documents/GEI/test/AnalyseProject/src/main/resources/wsdl/analyzer.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Analyzer(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Analyzer(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Analyzer() {
        super(WSDL_LOCATION, SERVICE);
    }

    public Analyzer(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public Analyzer(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public Analyzer(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns AnalyseChaineWS
     */
    @WebEndpoint(name = "AnalyseChaineWSPort")
    public AnalyseChaineWS getAnalyseChaineWSPort() {
        return super.getPort(AnalyseChaineWSPort, AnalyseChaineWS.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AnalyseChaineWS
     */
    @WebEndpoint(name = "AnalyseChaineWSPort")
    public AnalyseChaineWS getAnalyseChaineWSPort(WebServiceFeature... features) {
        return super.getPort(AnalyseChaineWSPort, AnalyseChaineWS.class, features);
    }

}
