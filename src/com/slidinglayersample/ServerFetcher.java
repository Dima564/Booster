package com.slidinglayersample;

import android.util.Log;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author bamboo
 * @since 3/22/14 10:54 AM
 */
public class ServerFetcher {

    public static final String TAG = "Tag-ServerFetcher";

    public static final String PREF_SEARCH_QUERY = "searchQuery";

    public static final String PREF_LAST_RESULT_ID = "lastResultId";

    public static final String ENDPOINT = "http://192.168.1.105:8000/";

    public static final String ID = "id";


    public static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    public static String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }


    public static String authorizeUser(User user) {

        try {


            String request = ENDPOINT + "api/auth.signin/?" +
                    "username=" + user.getNickname() + "&" +
                    "password=" + user.getPassword();


            String response = getUrl(request);

            Log.i("ololo", "authorization   :  " + response);

            JSONObject obj = (JSONObject) new JSONTokener(response)
                    .nextValue();

            String userId = obj.getString(ID);

            Log.i("ololo", "authorization   :  (" + userId + ")");

            return userId;

        } catch (Exception e) {
            Log.e(TAG, "Failed to authorize user", e);
        }

        return null;
    }


    public static String registerUser(User user) {

        try {

            String request = ENDPOINT + "api/auth.signup/?" +
                    "username=" + user.getNickname() + "&" +
                    "email=" + user.getEmail() + "&" +
                    "password=" + user.getPassword();

            String response = getUrl(request);
            JSONObject obj = (JSONObject) new JSONTokener(response)
                    .nextValue();

            String userId = obj.getString(ID);

            return userId;

        } catch (Exception e) {
            Log.e(TAG, "Failed to register user", e);
        }

        return null;
    }

    public static String getRegisterUrl(User user) {
        return ENDPOINT + "api/auth.signup/?" +
                "username=" + user.getNickname() + "&" +
                "email=" + user.getEmail() + "&" +
                "password=" + user.getPassword();
    }

}
