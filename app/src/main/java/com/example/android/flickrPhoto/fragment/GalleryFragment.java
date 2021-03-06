package com.example.android.flickrPhoto.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import com.reginald.swiperefresh.CustomSwipeRefreshLayout;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.android.flickrPhoto.R;
import com.example.android.flickrPhoto.util.GalleryAdapter;
import com.example.android.flickrPhoto.model.GalleryItem;
import com.example.android.flickrPhoto.util.URLManager;

public class GalleryFragment extends Fragment {

    // each requests was labeled by TAG before adding it to the request queue
    private static final String TAG = GalleryFragment.class.getSimpleName();

    //it defines the gallery columns
    private static final int COLUMN_NUM = 3;

    //it used to find how many pages should be load depends on flickr api result
    private static final int ITEM_PER_PAGE = 12;

    private RequestQueue mRq;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private CustomSwipeRefreshLayout mSwipeRefreshLayout;

    private GalleryAdapter mAdapter;

    private boolean mLoading = false;
    private boolean mHasMore = true;

    //it defines that the fragment should retrieve public or private photos depends on user's choice
    public String fragmentShowOptions;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRq = Volley.newRequestQueue(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(), COLUMN_NUM);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GalleryAdapter(getActivity(), new ArrayList<GalleryItem>());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItem = mLayoutManager.getItemCount();
                int lastItemPos = mLayoutManager.findLastVisibleItemPosition();
                if (mHasMore && !mLoading && totalItem - 1 != lastItemPos) {
                    startLoading();
                }
            }
        });

        mSwipeRefreshLayout = (CustomSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                new CustomSwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh();
                    }
                }
        );

        return view;
    }

    public void refresh() {
        mAdapter.clear();
        startLoading();
    }

    private void startLoading() {
        mLoading = true;

        String url;

        int totalItem = mLayoutManager.getItemCount();
        final int page = totalItem / ITEM_PER_PAGE + 1;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<GalleryItem> result = new ArrayList<>();
                try {
                    JSONObject photos = response.getJSONObject("photos");
                    if (photos.getInt("pages") == page){
                        mHasMore = false;
                    }
                    JSONArray photoArr = photos.getJSONArray("photo");
                    for (int i = 0; i < photoArr.length(); i++) {
                        JSONObject itemObj = photoArr.getJSONObject(i);
                        GalleryItem item = new GalleryItem(
                                itemObj.getString("id"),
                                itemObj.getString("secret"),
                                itemObj.getString("server"),
                                itemObj.getString("farm")
                        );
                        result.add(item);
                    }
                } catch (JSONException e) {

                }
                mAdapter.addAll(result);
                mAdapter.notifyDataSetChanged();
                mLoading = false;
                mSwipeRefreshLayout.refreshComplete();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        if (fragmentShowOptions.equals("showPublicPhotos")){
            url = URLManager.getInstance().getItemUrl(true);
        }
        else {
            if (fragmentShowOptions.equals("showPrivatePhotos")) {
                url = URLManager.getInstance().getItemUrl(false);
            } else {
                url = "";
            }
        }

        if (url != ""){
            //make a request for retrieving photos from flickr api
            JsonObjectRequest request = new JsonObjectRequest(url, null, listener, errorListener);

            //labeled the request
            request.setTag(TAG);

            //add the request to the queue
            mRq.add(request);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLoading();
    }

    private void stopLoading() {

        //it cancel all existed request in queue
        //we should do it when we want to switch between showing different kind of photos (public or private)
        if (mRq != null) {
            mRq.cancelAll(TAG);
        }

    }


}