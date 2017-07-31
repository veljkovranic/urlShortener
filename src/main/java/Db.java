import org.apache.commons.validator.routines.UrlValidator;

import java.net.URL;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by veljko on 7/31/17.
 */
public class Db {

    private static final int RADIX = 36;
    private static final String PREFIX = "sho.rt/";
    private static Db instance;

    private Map<String, UrlModel> mappingFromRawToShortened;
    private Map<String, UrlModel> mappingFromShortenedToRaw;

    private Db(){
        mappingFromRawToShortened = new ConcurrentHashMap<String, UrlModel>();
        mappingFromShortenedToRaw = new ConcurrentHashMap<String, UrlModel>();
    }

    public static Db getInstance(){
        if (instance == null) {
            return new Db();
        }
        return instance;
    }

    public UrlModel createNewEntry(String url) throws Exception{
        if (!isValid(url)){
            throw new Exception("Url is not valid");
        }
        if (mappingFromRawToShortened.containsKey(url)){
            return mappingFromRawToShortened.get(url);
        }

        int id = mappingFromRawToShortened.size();
        String generatedOutput = convertIntToBigBase(id);

        String completeUrl = generatedOutput;

        UrlModel urlModel = new UrlModel();
        urlModel.setRawValue(url);
        urlModel.setResultValue(completeUrl);

        mappingFromRawToShortened.put(url, urlModel);
        mappingFromShortenedToRaw.put(completeUrl, urlModel);

        return urlModel;
    }

    private boolean isValid(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    public UrlModel getOriginalUrl(String shortUrl) throws Exception{

        if (!mappingFromShortenedToRaw.containsKey(shortUrl)){
            throw new Exception("No key found");
        }

        if (mappingFromShortenedToRaw.get(shortUrl) == null) {
            throw new Exception("Key is null");
        }

        return mappingFromShortenedToRaw.get(shortUrl);
    }

    private String convertIntToBigBase(int input){
        return Integer.toString(input, RADIX);
    }

    private int convertBigBaseToInt(String input){
        return Integer.parseInt(input, RADIX);
    }
}
