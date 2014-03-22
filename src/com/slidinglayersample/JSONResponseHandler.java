package com.slidinglayersample;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

/**
 * @author bamboo
 * @since 3/22/14 1:45 PM
 */
public class JSONResponseHandler implements
        ResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {

        String JSONResponse = new BasicResponseHandler()
                .handleResponse(httpResponse);

        Log.i("ololo", JSONResponse);

        try {
            JSONObject object = (JSONObject) new JSONTokener(JSONResponse)
                    .nextValue();

            String id = object.getString(ServerFetcher.ID);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "-1";
    }
}