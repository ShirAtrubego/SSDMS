package main.CombinedObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CertificateTransparencyLog {

    String mxHost;

    public CertificateTransparencyLog(String mxHost) {
        this.mxHost = mxHost;
    }

    public boolean checkTransparency() throws IOException {

        final String USER_AGENT = "Mozilla/5.0";
        String urlTest = "https://certspotter.com/api/v0/certs?domain=";
        String url = urlTest.concat(mxHost);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GETx
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String responseString = response.toString();
        return responseString.contains("index");

    }
}
