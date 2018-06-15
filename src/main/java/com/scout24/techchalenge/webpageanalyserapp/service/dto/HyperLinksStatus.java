package com.scout24.techchalenge.webpageanalyserapp.service.dto;

import org.springframework.http.HttpStatus;

public class HyperLinksStatus {
    private String url;
    private boolean https;
    private int httpStatus;
    private boolean redirect;
    private String redirectURL;
    private String resourceException;
    public HyperLinksStatus(){

    }
    public HyperLinksStatus(String url, boolean https, int httpStatus, boolean redirect, String redirectURL, String resourceException) {
        this.url = url;
        this.https = https;
        this.httpStatus = httpStatus;
        this.redirect = redirect;
        this.redirectURL = redirectURL;
        this.resourceException = resourceException;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getResourceException() {
        return resourceException;
    }

    public void setResourceException(String resourceException) {
        resourceException = resourceException;
    }

    @Override
    public String toString() {
        return "HyperLinksStatus{" +
            "url='" + url + '\'' +
            ", https=" + https +
            ", httpStatus=" + httpStatus +
            ", redirect=" + redirect +
            ", redirectURL='" + redirectURL + '\'' +
            ", Exception='" + resourceException + '\'' +
            '}';
    }
}
