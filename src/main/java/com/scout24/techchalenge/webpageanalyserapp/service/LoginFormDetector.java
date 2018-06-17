package com.scout24.techchalenge.webpageanalyserapp.service;

import com.scout24.techchalenge.webpageanalyserapp.web.rest.errors.InternalServerErrorException;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginFormDetector {
    private static Logger log= LoggerFactory.getLogger(LoginFormDetector.class);
    public static boolean lookForLoginForm(String url) throws IOException {
        //grab login form page first
        Response loginPageResponse =
            Jsoup.connect(url)
                .validateTLSCertificates(false)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .followRedirects(true)
                .execute();



        //get the cookies from the response, which we will post to the action URL
        Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

        Document document = loginPageResponse.parse();
        String strActionURL = document.select("form").attr("action");
        Elements inputElementsInsideForm = document.select("form").select("input");

        return (inputElementsInsideForm.stream().filter(element -> "password".equals(element.attr("type"))).findFirst().isPresent() &&
            inputElementsInsideForm.stream().filter(element -> "text".equals(element.attr("type"))).findFirst().isPresent());

    }

    public static boolean attemptForLogin(String url)  {
        //grab login form page first
        Response loginPageResponse =
            null;
        try {
            loginPageResponse = Jsoup.connect(url)
                .validateTLSCertificates(false)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .followRedirects(true)
                .execute();
        } catch (IOException e) {
            log.debug("Error for loading login page : "+ e.getMessage());
            return false;

        }

        System.out.println("Fetched login page");

        //get the cookies from the response, which we will post to the action URL
        Map<String, String> mapLoginPageCookies = loginPageResponse.cookies();

        Document document = null;
        try {
            document = loginPageResponse.parse();
        } catch (IOException e) {
            log.debug("Error for pasring login page : "+ e.getMessage());
            return false;
        }
        String strActionURL = document.select("form").attr("action");
        Elements inputElementsInsideForm = document.select("form").select("input");

        //lets make data map containing all the parameters and its values found in the form
        Map<String, String> mapParams = new HashMap<String, String>();
        inputElementsInsideForm.stream().filter(inputElement -> !inputElement.attr("name").isEmpty()).forEach(inputElement -> {
            mapParams.put(inputElement.attr("name"), (inputElement.attr("value")));
            System.out.println("Key " + inputElement.attr("name") + " Value " + inputElement.attr("value"));
        });

        mapParams.put("f.loginName", "DummyUsername");
        mapParams.put("f.password", "Dummypassword");
        URL aURL = null;
        try {
            aURL = new URL(document.baseUri());
        } catch (MalformedURLException e) {
            log.debug("Error for pasring login page : "+ e.getMessage());
            return false;
        }
        System.out.println("actionURL " + strActionURL);
        String domain = aURL.getHost();
        Response responsePostLogin = null;
        try {
            responsePostLogin = Jsoup.connect(aURL.getProtocol() + "://" + domain + strActionURL)
                //referrer will be the login page's URL
                .validateTLSCertificates(false)
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
        } catch (IOException e) {
            log.debug("Error trying for login page with url :"+strActionURL+" s"+e.getMessage());
            return false;

        }

        System.out.println("HTTP Status Code: " + responsePostLogin.statusCode());

        //parse the document from response
        Document afterLogindocument = null;
        try {
            afterLogindocument = responsePostLogin.parse();
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return afterLogindocument.select("form").parallelStream().filter(element ->
            element.html().contains("Incorrect username or password")).findFirst().isPresent();


    }
}
