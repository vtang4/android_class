package com.example.v.newsapp;

import org.junit.Test;

import java.net.URL;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NetworkUnitTest {
    public static final String TAG = "NetworkUnitTest";

    @Test
    public void networkCallReturnsUsableJSON() throws Exception {
        URL url = new URL("https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=ccdbfccd10a14924a99d63668953dc2c");
        String result = NetworkUtils.getResponseFromHttpUrl(url);
        System.out.println(result);
        assert (result.contains("\"status\":\"ok\""));
    }
}