/**
 * Created by veljko on 7/31/17.
 */
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.google.gson.JsonParser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class NetClient {

    private static final String DEFAULT_COUNTRY_CODE = "RS";
    public static boolean DEBUG_LOG = false;

    private static final String HTTP_REQUEST_METHOD = "GET";
    private static final int HTTP_CONNECT_TIMEOUT = 10000;
    private static final int HTTP_READ_TIMEOUT = 10000;

    public static String callApi(String ip) {
        int numberOfTrys = 2;

        while (numberOfTrys > 0) {
            try {
                if (DEBUG_LOG) {
                    System.out.println("About to open connection with ip " + ip);
                }
                numberOfTrys--;

                URL url = new URL("http://ipapi.co/" + ip + "/json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDefaultUseCaches(false);
                httpURLConnection.setRequestMethod(HTTP_REQUEST_METHOD);

                httpURLConnection.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
                httpURLConnection.setReadTimeout(HTTP_READ_TIMEOUT);

                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();
                if (DEBUG_LOG) {
                    System.out.println("responseCode " + responseCode);
                }

                InputStream httpInputStream = httpURLConnection.getInputStream();
                Scanner input = new Scanner(httpInputStream);
                String responseString = input.useDelimiter("\\A").next();
                input.close();

                try {
                    JSONObject jsonObjectResponse = new JSONObject(responseString);
                    String countryCode = jsonObjectResponse.optString("country");
                    if (countryCode == null) {
                        countryCode = DEFAULT_COUNTRY_CODE;
                    }
                    if (DEBUG_LOG) {
                        System.out.println(countryCode + " COUNTRY CODE");
                    }
                    httpURLConnection.disconnect();
                    return countryCode;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if (DEBUG_LOG) {
                    System.out.println("About to close a connection");
                }
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }
        }
        return null;
    }

}