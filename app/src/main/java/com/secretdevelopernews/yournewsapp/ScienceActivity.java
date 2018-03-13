package com.secretdevelopernews.yournewsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ScienceActivity extends BaseActivity {
    String API_KEY = "646fffb9629a40e5a5d099caedd4a572"; // ### YOUE NEWS API HERE ###
    String NEWS_SOURCE = "google-news-in";
    ListView listNews;
    ProgressBar loader;
    HashMap<String, String> map;

    HashMap<String, String> listDataChild;
    ExpandableListView expandableListView;

    String linkUrl = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=646fffb9629a40e5a5d099caedd4a572";



    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science);

        DownloadNews newsTask = new DownloadNews();



        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);


       // listNews = (ListView) findViewById(R.id.listViewScience);
        loader = (ProgressBar) findViewById(R.id.loaderScience);
        expandableListView = (ExpandableListView) findViewById(R.id.listViewScience) ;
        expandableListView.setEmptyView(loader);
        ExpandableRecyclerAdapter expandableRecyclerAdapter = new ExpandableRecyclerAdapter(this,ScienceActivity.this,
                dataList,listDataChild);
        expandableListView.setAdapter(expandableRecyclerAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {
                Intent i = new Intent(ScienceActivity.this, DetailsActivity.class);
                i.putExtra("url", dataList.get(+position).get(KEY_URL));
                startActivity(i);
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int position, int i1, long l) {
                Intent intent = new Intent(ScienceActivity.this, DetailsActivity.class);
                intent.putExtra("url", dataList.get(+position).get(KEY_URL));
                startActivity(intent);
                return false;
            }
        });

        //  ListNewsAdapter adapter = new ListNewsAdapter(ScienceActivity.this, dataList);
        //  listNews.setAdapter(adapter);

       /* listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(ScienceActivity.this, DetailsActivity.class);
                i.putExtra("url", dataList.get(+position).get(KEY_URL));
                startActivity(i);
            }
        });*/






        if(Function.isNetworkAvailable(getApplicationContext()))
        {


            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            return;

        }

    }


    //indicator change for expandable list view
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expandableListView.setIndicatorBounds(expandableListView.getRight()- 60, expandableListView.getWidth());
    }


    public class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {


            String urlParameters = "";
            // xml = Function.excuteGet("https://newsapi.org/v1/articles?source="+NEWS_SOURCE+"&sortBy=top&apiKey="+API_KEY, urlParameters);
            String xml = Function.excuteGet(linkUrl, urlParameters);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) { // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        map = new HashMap<String, String>();

                        //child map
                        listDataChild = new HashMap<String, String>();


                        map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());
                        dataList.add(map);

                        //child map
                        listDataChild.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());


                        Log.d(" string", jsonObject.optString(KEY_TITLE).toString());
                        Log.d(" dstring", jsonObject.optString(KEY_DESCRIPTION).toString());
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                ExpandableRecyclerAdapter adapter = new ExpandableRecyclerAdapter(ScienceActivity.this,ScienceActivity.this, dataList,listDataChild);
                expandableListView.setAdapter(adapter);

                expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(ScienceActivity.this, DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

             /*  ListNewsAdapter adapter = new ListNewsAdapter(ScienceActivity.this, dataList);
               listNews.setAdapter(adapter);

               listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   public void onItemClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                       Intent i = new Intent(ScienceActivity.this, DetailsActivity.class);
                       i.putExtra("url", dataList.get(+position).get(KEY_URL));
                       startActivity(i);
                   }
               });
*/
            } else {
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
