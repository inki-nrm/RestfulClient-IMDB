/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testingjersey;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Att skapa tjänst ? ej testat :
 * http://netbeans.dzone.com/articles/building-simple-rest-service read 
 * :testa även http://code.google.com/p/google-gson/ ( tips från Skogiz )
 *
 * @author Ingimar
 */
public class TestingJersey {

    /*
     * i string (optional) a valid IMDb movie id t string (optional) title of a
     * movie to search for y year (optional) year of the movie r JSON, XML
     * response data type (JSON default) plot short, full short or extended plot
     * (short default) callback name (optional) JSONP callback name tomatoes
     * true (optional) adds rotten tomatoes data
     */
    public static void main(String[] args) {
        System.out.println("Starting Jersey");
        Client client = Client.create();
        // String url = "http://www.imdbapi.com/";
        String url = "http://www.omdbapi.com/";
        WebResource webResource = client.resource(url);

        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

        queryParams.add("s", "Star Wars");
        String reply = TestingJersey.RETURN_TYPES.JSON.toString();
        queryParams.add("r", reply);
        String resultFromIMDB = webResource.queryParams(queryParams).get(String.class);
        System.out.println("Result" + resultFromIMDB);

        if (reply.equals("JSON")) {
            try {
                parseJSON(resultFromIMDB);
            } catch (JSONException ex) {
                Logger.getLogger(TestingJersey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void parseJSON(String result) throws JSONException {
        JSONObject myjson = new JSONObject(result);


        JSONArray nameArray = myjson.names();
        JSONArray valArray = myjson.toJSONArray(nameArray);
        for (int i = 0; i < valArray.length(); i++) {
            String part = nameArray.getString(i) + "," + valArray.getString(i);
            System.out.println("-: " + part);
        }
    }

    public static enum RETURN_TYPES {

        JSON, XML;

    }
}
