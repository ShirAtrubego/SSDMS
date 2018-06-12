package main.CombinedObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CertificateTransparencyCheck {

    String mx;

    public CertificateTransparencyCheck(String mx) {
        this.mx = mx;
    }

    public boolean checkTransparency() throws IOException {

        final String USER_AGENT = "Mozilla/5.0";
        String urlTest = "https://certspotter.com/api/v0/certs?domain=";
        String url;
        URL obj;
        HttpURLConnection con;
        BufferedReader in;
        StringBuffer response;
        String responsteString;


        url = urlTest.concat(mx);

        obj = new URL(url);
        con = (HttpURLConnection) obj.openConnection();

        // optional default is GETx
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }


        in.close();

        responsteString = response.toString();

       return responsteString.contains("index");

    }
}
