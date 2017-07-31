/**
 * Created by veljko on 7/30/17.
 */
import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    public String render(Object model) {
        return gson.toJson(model);
    }

}