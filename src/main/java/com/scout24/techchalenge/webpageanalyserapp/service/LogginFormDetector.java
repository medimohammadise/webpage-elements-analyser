package com.scout24.techchalenge.webpageanalyserapp.service;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LogginFormDetector {

    public boolean lookForLoginForm(String url) throws IOException {
        //grab login form page first
        Response loginPageResponse =
            Jsoup.connect(url)
                //.referrer("https://github.com")
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .followRedirects(true)
                .execute();

        System.out.println("Fetched login page");

        //get the cookies from the response, which we will post to the action URL
        Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

        Document document = loginPageResponse.parse();
        String strActionURL = document.select("form").attr("action");
        Elements inputElementsInsideForm = document.select("form").select("input");

        return (inputElementsInsideForm.stream().filter(element -> "password".equals(element.attr("type"))).findFirst().isPresent() &&
            inputElementsInsideForm.stream().filter(element -> "text".equals(element.attr("type"))).findFirst().isPresent());

    }

    public boolean attemptForLogin(String url) throws IOException {
        //grab login form page first
        Response loginPageResponse =
            Jsoup.connect(url)
                //.referrer("https://github.com")
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .followRedirects(true)
                .execute();

        System.out.println("Fetched login page");

        //get the cookies from the response, which we will post to the action URL
        Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

        Document document = loginPageResponse.parse();
        String strActionURL = document.select("form").attr("action");
        Elements inputElementsInsideForm = document.select("form").select("input");

            //lets make data map containing all the parameters and its values found in the form
            Map<String, String> mapParams = new HashMap<String, String>();
            inputElementsInsideForm.stream().filter(inputElement->!inputElement.attr("name").isEmpty()).forEach(inputElement-> {
                mapParams.put(inputElement.attr("name"), (inputElement.attr("value")));
                System.out.println("Key " + inputElement.attr("name") + " Value " + inputElement.attr("value"));
            });
       // mapParams.put("proceed", "Go");
            /*mapParams.put("authenticity_token", authToken);
            mapParams.put("utf8", "e2 9c 93");
            mapParams.put("login", "YOUR_USER_ID");
            mapParams.put("password", "YOUR_PASSWORD");
            mapParams.put("commit", "Sign in");
            mapParams.put("proceed", "Go");*/

        mapParams.put("f.loginName","dsdsdsdsdsdsd");
        mapParams.put("f.password","dsdsdsdsdsdsd");
        URL aURL = new URL(document.baseUri());
        System.out.println("actionURL "+strActionURL);
        String domain = aURL.getHost();
            Response responsePostLogin = Jsoup.connect(aURL.getProtocol()+"://"+domain+strActionURL)
                //referrer will be the login page's URL
                //.referrer(url)
                .method(Connection.Method.POST)
                //user agent
                .userAgent("Mozilla/5.0")
                //connect and read time out
                .timeout(10 * 1000)
                //post parameters
                .data(mapParams)
                //cookies received from login page
                .cookies(mapLoginPageCookies)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                //many websites redirects the user after login, so follow them
                .followRedirects(true)
                .execute();

            System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

            //parse the document from response
            Document afterLogindocument = responsePostLogin.parse();
            //System.out.println(afterLogindocument);

                return afterLogindocument.select("form").parallelStream().filter(element ->
                    element.html().contains("Incorrect username or password")).findFirst().isPresent();



    }
    public static void main(String[] args) throws IOException {
        LogginFormDetector logginFormDetector =new LogginFormDetector();
        System.out.print(logginFormDetector.lookForLoginForm("https://www.spiegel.de/meinspiegel/login.html"));
    }
}
