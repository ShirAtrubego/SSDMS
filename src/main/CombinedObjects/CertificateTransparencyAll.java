package main.CombinedObjects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CertificateTransparencyAll {

    String hostname;

    public CertificateTransparencyAll(String hostname) {
        this.hostname = hostname;
    }

    private final String USER_AGENT = "Mozilla/5.0";
    private String urlTest = "https://certspotter.com/api/v0/certs?domain=";

    public String getAllTransparencyEntries(String host) throws Exception {

        String hostName = urlTest.concat(host);

        URL obj = new URL(hostName);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GETx
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
