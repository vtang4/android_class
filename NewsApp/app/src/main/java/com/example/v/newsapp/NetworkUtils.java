package com.example.v.newsapp;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String NEWS_BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=ccdbfccd10a14924a99d63668953dc2c";

    final static String PARAM_QUERY = "q";



    public static URL buildUrl(String newsSearchQuery) {
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, newsSearchQuery)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }


    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL[] urls) {


            String newsSearchResult = null;

            URL url = NetworkUtils.buildUrl(newsSearchResult);



            try {

                newsSearchResult = NetworkUtils.getResponseFromHttpUrl(url);

            } catch (IOException e) {

                e.printStackTrace();

            }

            return newsSearchResult;
        }



    }



}


