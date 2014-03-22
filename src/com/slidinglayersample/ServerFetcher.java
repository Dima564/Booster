package com.slidinglayersample;

import android.util.Log;

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

    public static final String TAG = "Tag-PhotoFetcher";

    public static final String PREF_SEARCH_QUERY = "searchQuery";

    public static final String PREF_LAST_RESULT_ID = "lastResultId";

    public static final String ENDPOINT = "http://192.168.1.105:8000/";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
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


    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }


    public String registerUser(User user) {

        try {


        } catch (Exception e) {
            Log.e(TAG, "Failed to fetch items", e);
        }

        return null;
    }

    public ArrayList<GalleryItem> downloadGalleryItems(String url) {

        ArrayList<GalleryItem> list = new ArrayList<GalleryItem>();

        try {
            String xmlString = getUrl(url);

//            Log.i(TAG, "Received xml: " + xmlString);

            XmlPullParserFactory factory = null;


            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));

            parseItems(list, parser);

            return list;


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }

        return list;
    }

    public ArrayList<GalleryItem> fetchItems(/*Integer page*/) {

        String url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("method", METHOD_GET_RECENT)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
//                .appendQueryParameter(PARAM_OPTIONAL_PAGE, page.toString())
//                .appendQueryParameter(PARAM_OPTIONAL_PER_PAGE, NUMBER_PER_PAGE)
                .build().toString();
        return downloadGalleryItems(url);
    }

    public ArrayList<GalleryItem> search(String query) {
        String url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("method", METHOD_SEARCH)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter(PARAM_EXTRAS, EXTRA_SMALL_URL)
                .appendQueryParameter(PARAM_TEXT, query)
                .build().toString();
        return downloadGalleryItems(url);
    }


    void parseItems(ArrayList<GalleryItem> items, XmlPullParser parser) throws IOException, XmlPullParserException {

        int eventType = parser.next();

        while (XmlPullParser.END_DOCUMENT != eventType) {

            if (eventType == XmlPullParser.START_TAG
                    && XML_PHOTO.equals(parser.getName())) {

                String id = parser.getAttributeValue(null, "id");
                String caption = parser.getAttributeValue(null, "title");
                String smallUrl = parser.getAttributeValue(null, EXTRA_SMALL_URL);
                String owner = parser.getAttributeValue(null, "owner");


                GalleryItem item = new GalleryItem();
                item.setId(id)
                        .setCaption(caption)
                        .setUrl(smallUrl)
                        .setOwner(owner);
                items.add(item);
            }
            eventType = parser.next();
        }
    }
}
