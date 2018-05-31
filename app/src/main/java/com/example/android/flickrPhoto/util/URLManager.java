package com.example.android.flickrPhoto.util;

import android.net.Uri;

public class URLManager {

    public static final String API_KEY = "8a565fe0436cc19973c4959b930691a6";
    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final int page = 5;
    private static final String searchText = "sea";
    private static volatile URLManager instance = null;

    private URLManager() {

    }

    public static URLManager getInstance() {
        if (instance == null) {
            synchronized (URLManager.class) {
                if (instance == null) {
                    instance = new URLManager();
                }
            }
        }
        return instance;
    }

    public static String getItemUrl() {
        String url;
        url = Uri.parse(ENDPOINT).buildUpon()
                .appendQueryParameter("method", METHOD_SEARCH)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("text", searchText)
                .appendQueryParameter("page", String.valueOf(page))
                .build().toString();
        return url;
    }
}
