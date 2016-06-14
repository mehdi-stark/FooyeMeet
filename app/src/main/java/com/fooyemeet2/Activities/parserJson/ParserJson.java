package com.fooyemeet2.Activities.parserJson;

import com.fooyemeet2.Activities.Fragments.Profil;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by kegan on 25/04/2016.
 */
public class ParserJson {
    public static String parserLogin(String result) {
        JSONObject objecttJson;
        String token = "false";
        if (result != "false")
        {
            try {
                objecttJson = new JSONObject(result);
                if (!objecttJson.isNull("token")){
                    token = objecttJson.getString("token");
                }
                else {
                    token = "false";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            token = "code0";
        }
        return token;
    }

    public static Profil parserGetProfil(String result) {
        ObjectMapper objectMapper = null;
        JsonFactory jsonFactory = null;
        Profil               profilsList = null;
        JsonParser jp = null;
        objectMapper    =     new ObjectMapper();
        jsonFactory     =     new JsonFactory();

        if (!result.equals("false")) {
            try{
                jp = jsonFactory.createJsonParser(result);
                profilsList = objectMapper.readValue(jp, Profil.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return profilsList;
    }
}
