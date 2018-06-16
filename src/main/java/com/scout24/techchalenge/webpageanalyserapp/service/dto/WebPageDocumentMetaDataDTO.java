package com.scout24.techchalenge.webpageanalyserapp.service.dto;

import java.util.HashMap;
import java.util.Map;

public class WebPageDocumentMetaDataDTO {
    private String htmlVersion;
    private String title;
    private Map<String, Integer> numberOfHeadingByGroupLevelMap = new HashMap<>();
    private int numberOfInternalLinks;
    private int numberOfExternalLinks;
    private boolean hasLoginForm;
    private boolean loginAttemptSucessFull;


    public WebPageDocumentMetaDataDTO(String htmlVersion, String title, Map<String, Integer> numberOfHeadingByGroupLevelMap, int numberOfInternalLinks, int numberOfExternalLinks, boolean hasLoginForm,
                                      boolean loginAttemptSucessFull) {
        this.htmlVersion = htmlVersion;
        this.title = title;
        this.numberOfHeadingByGroupLevelMap = numberOfHeadingByGroupLevelMap;
        this.numberOfInternalLinks = numberOfInternalLinks;
        this.numberOfExternalLinks = numberOfExternalLinks;
        this.hasLoginForm = hasLoginForm;
        this.loginAttemptSucessFull=loginAttemptSucessFull;
    }

    public String getHtmlVersion() {
        return htmlVersion;
    }

    public void setHtmlVersion(String htmlVersion) {
        this.htmlVersion = htmlVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Integer> getNumberOfHeadingByGroupLevelMap() {
        return numberOfHeadingByGroupLevelMap;
    }

    public void setNumberOfHeadingByGroupLevelMap(Map<String, Integer> numberOfHeadingByGroupLevelMap) {
        this.numberOfHeadingByGroupLevelMap = numberOfHeadingByGroupLevelMap;
    }

    public int getNumberOfInternalLinks() {
        return numberOfInternalLinks;
    }

    public void setNumberOfInternalLinks(int numberOfInternalLinks) {
        this.numberOfInternalLinks = numberOfInternalLinks;
    }

    public int getNumberOfExternalLinks() {
        return numberOfExternalLinks;
    }

    public void setNumberOfExternalLinks(int numberOfExternalLinks) {
        this.numberOfExternalLinks = numberOfExternalLinks;
    }

    public boolean isHasLoginForm() {
        return hasLoginForm;
    }

    public void setHasLoginForm(boolean hasLoginForm) {
        this.hasLoginForm = hasLoginForm;
    }

    public boolean isLoginAttemptSucessFull() {
        return loginAttemptSucessFull;
    }

    public void setLoginAttemptSucessFull(boolean loginAttemptSucessFull) {
        this.loginAttemptSucessFull = loginAttemptSucessFull;
    }
}
