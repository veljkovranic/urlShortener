/**
 * Created by veljko on 7/28/17.
 */
import com.google.gson.JsonObject;

import static spark.Spark.*;


public class Main {

    public static Db database = Db.getInstance();
    public static Stats stats = Stats.getInstance();

    public static void main(String[] args) {
        post("/shortenUrl", (request, response) -> {
            String rawUrl = request.queryParams("url");
            System.out.println("input  " + rawUrl);

            if(!rawUrl.startsWith("http://") && !rawUrl.startsWith("https://")){
                rawUrl = "http://" + rawUrl;
            }

            try {
                UrlModel urlModel = database.createNewEntry(rawUrl);
                response.status(200);
                return urlModel;
            } catch (Exception e) {
                response.status(400);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("errorMessage",e.getMessage().toString());
                response.body(jsonObject.toString());
            }

            return response;
        }, new JsonTransformer());

        get("/open/:url", (request, response) -> {
            String rawUrl = request.params("url");

            String requestIp = request.ip();

            try {
                UrlModel urlModel = database.getOriginalUrl(rawUrl);
                String realLocation = urlModel.getRawValue();
                stats.insertEvent(requestIp);
                response.redirect(realLocation);
            } catch (Exception e){
                response.status(400);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("errorMessage",e.getMessage().toString());
                response.body(jsonObject.toString());
                System.out.println(e.getMessage());
            }
            System.out.println("END");
            return response;
        }, new JsonTransformer());

        get("/stats", (request, response) -> {
            response.type("application/json");
            return stats.getAllStats();
        }, new JsonTransformer());


    }
}