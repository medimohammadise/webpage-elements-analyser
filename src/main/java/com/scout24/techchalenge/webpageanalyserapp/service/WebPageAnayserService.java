package com.scout24.techchalenge.webpageanalyserapp.service;

import com.scout24.techchalenge.webpageanalyserapp.service.dto.WebPageDocumentMetaDataDTO;
import com.scout24.techchalenge.webpageanalyserapp.web.rest.errors.InputDataInvalidException;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebPageAnayserService {
    Document document;
    String url;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public void connectToDocument(String url) throws IOException {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) throw new InputDataInvalidException();
        this.document = Jsoup.connect(url).timeout(10 * 1000).validateTLSCertificates(false).get();
        this.url = url;
    }

    private String getHtmlVersion() {
        return document.childNodes().stream().filter(node -> node instanceof DocumentType).map(this::generateHtmlVersion).collect(Collectors.joining());
    }

    private String generateHtmlVersion(Node node) {
        DocumentType documentType = (DocumentType) node;
        String htmlVersion = documentType.attr("publicid");
        //log.info("htmlVersion"+ htmlVersion);
        return "".equals(htmlVersion) ? "5" : htmlVersion;
    }

    private String getPageTitle() {
        return document.title();
    }

    private Map<String, Integer> getNumberofHeading() {
        // Group of all h-Tags
        Map<String, Integer> numberOfHeadingGroupByHeadingLevel = new HashMap<>();
        numberOfHeadingGroupByHeadingLevel.put("h1", document.select("h1").size());
        numberOfHeadingGroupByHeadingLevel.put("h2", document.select("h2").size());
        numberOfHeadingGroupByHeadingLevel.put("h3", document.select("h3").size());
        numberOfHeadingGroupByHeadingLevel.put("h4", document.select("h4").size());
        numberOfHeadingGroupByHeadingLevel.put("h5", document.select("h5").size());
        numberOfHeadingGroupByHeadingLevel.put("h6", document.select("h6").size());
        return numberOfHeadingGroupByHeadingLevel;
    }

    private Map<String, Integer> getNumberofExternalInternalHyperLinks() throws MalformedURLException {
        URL aURL = new URL(document.baseUri());
        String domain = aURL.getHost();

        Map<String, Integer> numberOfHeadingGroupByHeadingLevel = new HashMap<>();
        numberOfHeadingGroupByHeadingLevel.put("Internal Domain Links", document.select("a").stream().filter(hrefElement -> hrefElement.absUrl("href").contains(domain)).collect(Collectors.toList()).size()
        );
        numberOfHeadingGroupByHeadingLevel.put("External Domain Links", document.select("a").stream().filter(hrefElement -> !hrefElement.absUrl("href").contains(domain)).collect(Collectors.toList()).size()
        );

        return numberOfHeadingGroupByHeadingLevel;
    }

    public Elements getHyperLinks() {
        // Group of all h-Tags
        return document.select("a");
    }

    public WebPageDocumentMetaDataDTO analyseWebPage(String url) throws IOException {

        this.connectToDocument(url);
        return new WebPageDocumentMetaDataDTO(getHtmlVersion(), getPageTitle(), getNumberofHeading(), getNumberofExternalInternalHyperLinks().get("Internal Domain Links"),
            getNumberofExternalInternalHyperLinks().get("External Domain Links"), LoginFormDetector.lookForLoginForm(url), LoginFormDetector.attemptForLogin(url));

    }

}
