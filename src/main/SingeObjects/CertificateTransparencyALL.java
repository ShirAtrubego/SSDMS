package main.SingeObjects;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CertificateTransparencyALL {

    private final String USER_AGENT = "Mozilla/5.0";
    private String urlTest = "https://certspotter.com/api/v0/certs?domain=";
//    private String host;// = "hsr.ch";
//    private String url = urlTest.concat(host);

    public String getAllTransparencyEntries(String host) throws Exception {

     String url = urlTest.concat(host);
     String   hostName = url;


        URL obj = new URL(hostName);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GETx
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);


        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();



    }

    public void printAllTransparencyEntries() throws Exception {
/*
        System.out.println("\nSending 'GET' request to URL : " + url);
//       System.out.println("Response Code : " + getAllTransparencyEntries(host).con.getResponseCode());
            String[] tokens = getAllTransparencyEntries(host).split("/\\{(.*?)\\}/g}");
            for (String t : tokens)
                         System.out.println(t);
*/
        }

}




