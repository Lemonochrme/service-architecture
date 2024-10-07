package fr.insa.ws.soap;

import fr.insa.ws.analyse.*;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        AnalyseChaineWS toto = new AnalyseChaineWS();
        System.out.println(toto.analyser("toto"));
    }
}
