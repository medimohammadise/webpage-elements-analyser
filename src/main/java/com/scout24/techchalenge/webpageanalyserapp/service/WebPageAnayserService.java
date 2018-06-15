package com.scout24.techchalenge.webpageanalyserapp.service;

import liquibase.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WebPageAnayserService {
    Document document;
    String url;
    private final Logger log = LoggerFactory.getLogger(getClass());
    public WebPageAnayserService(String url) throws IOException{
        this.document=Jsoup.connect(url).get();
        this.url=url;
    }

    public String getHtmlVersion(){
        return document.childNodes().stream().filter(node->node instanceof DocumentType).map(this::generateHtmlVersion).collect(Collectors.joining());
    }
    private String generateHtmlVersion(Node node)
    { DocumentType documentType = (DocumentType) node;
        String htmlVersion = documentType.attr("publicid");
        //log.info("htmlVersion"+ htmlVersion);
        return "".equals(htmlVersion) ? "5": htmlVersion;
    }
    public String getPageTitle(){
        return document.title();
    }

    public Map<String,Integer> getNumberofHeading(){
        // Group of all h-Tags
        Map<String,Integer> numberOfHeadingGroupByHeadingLevel=new HashMap<>();
        numberOfHeadingGroupByHeadingLevel.put("h1", document.select("h1").size());
        numberOfHeadingGroupByHeadingLevel.put("h2", document.select("h2").size());
        numberOfHeadingGroupByHeadingLevel.put("h3", document.select("h3").size());
        numberOfHeadingGroupByHeadingLevel.put("h4", document.select("h4").size());
        numberOfHeadingGroupByHeadingLevel.put("h5", document.select("h5").size());
        numberOfHeadingGroupByHeadingLevel.put("h6", document.select("h6").size());
        return numberOfHeadingGroupByHeadingLevel;
    }
    public Map<String,Integer> getNumberofExternalInternalHyperLinks(){
        // Group of all h-Tags
        Map<String,Integer> numberOfHeadingGroupByHeadingLevel=new HashMap<>();
        numberOfHeadingGroupByHeadingLevel.put("Internal Domain Links", document.select("a[href*="+"www.elizabethcastro.com"+"]").size());
        numberOfHeadingGroupByHeadingLevel.put("External Domain Links", document.select("a[href]:not(:has("+"www.elizabethcastro.com"+"))").size());

        return numberOfHeadingGroupByHeadingLevel;
    }
    public Elements getHyperLinks(){
        // Group of all h-Tags
       return  document.select("a");

    }

    /*public static void main(String[] args){
        WebPageAnayserService webPageAnayserService;
        try {
            webPageAnayserService = new WebPageAnayserService("http://www.elizabethcastro.com/");
            webPageAnayserService.log.info("HTML Version : "+webPageAnayserService.getHtmlVersion());
            webPageAnayserService.log.info("Title        : "+webPageAnayserService.getPageTitle());
            webPageAnayserService.getNumberofHeading().forEach((header,number)->System.out.println(header+" "+number));
            webPageAnayserService.getNumberofExternalInternalHyperLinks().forEach((header,number)->System.out.println(header+" "+number));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
