package com.example.android.flickrPhoto.util;

import android.net.Uri;

public class URLManager {

    //its my Flickr API key:
    public static final String API_KEY = "8a565fe0436cc19973c4959b930691a6";

    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";

    //api which used to retrieve public photos:
    private static final String METHOD_SEARCH = "flickr.photos.search";

    //api which used to retrieve private photos:
    private static final String METHOD_PRIVATE_SEARCH = "flickr.people.getPhotos";

    //my Flickr user id
    private static final String USER_ID = "154537064@N03";

    //define number of pages for rest call
    private static final int page = 5;

    //serch "sea" photos in public photos and retrieve them
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

    //make the url for retrieving photos from Flickr
    public static String getItemUrl(boolean showPublicPhotos) {
        String url;
        if (showPublicPhotos){
            url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", METHOD_SEARCH)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("text", searchText)
                    .appendQueryParameter("page", String.valueOf(page))
                    .build().toString();
        }
        else {
            url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", METHOD_PRIVATE_SEARCH)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("user_id", USER_ID)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .build().toString();
        }
        return url;
    }
}
