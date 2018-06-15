package com.scout24.techchalenge.webpageanalyserapp.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class LogginFormDetector {
    /*public static void main(String[] args) {


        try {

            //grab login form page first
            Response loginPageResponse =
                Jsoup.connect("https://github.com/login")
                    .referrer("https://github.com")
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .followRedirects(true)
                    .execute();

            System.out.println("Fetched login page");

            //get the cookies from the response, which we will post to the action URL
            Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

            String authToken = loginPageResponse.parse().select("input[name=\"authenticity_token\"]")
                .first()
                .attr("value");


            //lets make data map containing all the parameters and its values found in the form
            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("authenticity_token", authToken);
            mapParams.put("utf8", "e2 9c 93");
            mapParams.put("login", "YOUR_USER_ID");
            mapParams.put("password", "YOUR_PASSWORD");
            mapParams.put("commit", "Sign in");
            //mapParams.put("proceed", "Go");

            //URL found in form's action attribute
            String strActionURL = "https://github.com/session";

            Response responsePostLogin = Jsoup.connect(strActionURL)
                //referrer will be the login page's URL
                .referrer("https://github.com/login")
                .method(Connection.Method.POST)
                //user agent
                .userAgent("Mozilla/5.0")
                //connect and read time out
                .timeout(10 * 1000)
                //post parameters
                .data(mapParams)
                //cookies received from login page
                .cookies(mapLoginPageCookies)
                //many websites redirects the user after login, so follow them
                .followRedirects(true)
                .execute();

            System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

            //parse the document from response
            Document document = responsePostLogin.parse();
            System.out.println(document);

            //get the cookies
            Map<String, String> mapLoggedInCookies = responsePostLogin.cookies();

            /*
             * For all the subsequent requests, you need to send
             * the mapLoggedInCookies containing cookies
             */

       /* } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/
}
