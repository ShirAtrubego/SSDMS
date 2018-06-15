package main.SingeObjects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CertificateTransparencyCheck {


    private void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public boolean checkTransparencyEntry(String name) throws Exception {

        disableWarning();
        MXRecordListCheck checkMX = new MXRecordListCheck();
        String [] host = new String[2];
        final String USER_AGENT = "Mozilla/5.0";
        String urlTest = "https://certspotter.com/api/v0/certs?domain=";
        String url;
        URL obj;
        HttpURLConnection con;
        BufferedReader in;
        StringBuffer response;
        String responsteString;
        boolean test = false;

        for(int i = 0; true; i++) {

            host[i] = String.valueOf(checkMX.checkMXRecordList(name));
            url = urlTest.concat(host[i]);

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

    public void printMailTransparencyEntries() throws Exception {

    }


}


