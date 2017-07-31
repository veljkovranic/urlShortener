import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by veljko on 7/31/17.
 */
public class Stats {

    private static Stats instance;

    private static Map<String, Integer> mappingFromLocationToNumberOfRedirections;

    private Stats(){
        mappingFromLocationToNumberOfRedirections = new ConcurrentHashMap<String, Integer>();
    }

    public static Stats getInstance(){
        if (instance == null) {
            return new Stats();
        }
        return instance;
    }

    public void insertEvent(String ip){
        Integer previousValue = 0;
        String countryCode = getCountryCodeForIp(ip);

        if (!mappingFromLocationToNumberOfRedirections.containsKey(countryCode)) {
            mappingFromLocationToNumberOfRedirections.put(countryCode, 1);
        } else {
            mappingFromLocationToNumberOfRedirections.put(countryCode, mappingFromLocationToNumberOfRedirections.get(countryCode) + 1);
        }

    }

    public String getCountryCodeForIp(String ip){
        return NetClient.callApi(ip);
    }

    public Map<String, Integer> getAllStats(){
        return mappingFromLocationToNumberOfRedirections;
    }
}
