package com.example.petplant.addplant;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
public class searchClient {

    private static final String API_BASE_URL = "https://trefle.io/api/plants?token=aUVNSXhKdTZFbGZ2cGprbzRFRkZSZz09&q=";
    private AsyncHttpClient client;
    public ArrayList<PlantProfileCard> lp = new ArrayList<>();

    public searchClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getSearch(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl(query);
            client.get(url , handler);
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}
