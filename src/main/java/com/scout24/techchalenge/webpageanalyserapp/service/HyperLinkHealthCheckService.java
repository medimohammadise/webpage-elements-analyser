package com.scout24.techchalenge.webpageanalyserapp.service;

import com.scout24.techchalenge.webpageanalyserapp.service.dto.HyperLinksHealthStatus;
import com.scout24.techchalenge.webpageanalyserapp.web.rest.errors.InternalServerErrorException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HyperLinkHealthCheckService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public List<HyperLinksHealthStatus> analyseHyperLinksStatus(String url) {

        WebPageAnayserService webPageAnayserService = new WebPageAnayserService();
        try {
            webPageAnayserService.connectToDocument(url);
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to connect to the web page");
        }
        return webPageAnayserService.getHyperLinks().parallelStream().filter(element -> !"".equals(element.absUrl("href"))).map(new Function<Element, HyperLinksHealthStatus>() {
                                                                                                                                                      @Override
                                                                                                                                                      public HyperLinksHealthStatus apply(Element element) {

                                                                                                                                                          try {
                                                                                                                                                              try {
                                                                                                                                                                  return checkResource(url, element.absUrl("href")).get();
                                                                                                                                                              } catch (MalformedURLException e) {
                                                                                                                                                                 log.debug("error processing url "+element.absUrl("href"));
                                                                                                                                                              }
                                                                                                                                                          } catch (InterruptedException e) {
                                                                                                                                                              log.debug("error occured dring check resource url "+element.absUrl("href"));

                                                                                                                                                          } catch (ExecutionException e) {
                                                                                                                                                              log.debug("error occured dring check resource url "+element.absUrl("href"));

                                                                                                                                                          }
                                                                                                                                                          return new HyperLinksHealthStatus();
                                                                                                                                                      }
                                                                                                                                                  }


        ).collect(Collectors.toList());
    }

    @Async
    public CompletableFuture<HyperLinksHealthStatus> checkResource(String url, String hyperLinkUrl) throws MalformedURLException {
        hyperLinkUrl = hyperLinkUrl.replace("../", "");  //remove relative addresss part because we are extracting absolutePath
        log.info("processing url " + hyperLinkUrl);
        URL hyperLinkURI = new URL(hyperLinkUrl);

        Connection.Response response = null;
        String resourceCheckErrorMessage = "";
        HyperLinksHealthStatus hyperLinksHealthStatus = null;
        try {
            response = Jsoup.connect(hyperLinkUrl).method(Connection.Method.GET).timeout(60*1000).validateTLSCertificates(false).followRedirects(true).execute();
        } catch (Exception e) {
            resourceCheckErrorMessage = e.getMessage();
        } finally {
            if (response != null)
                hyperLinksHealthStatus = new HyperLinksHealthStatus(hyperLinkUrl,
                    ("https".equals(hyperLinkURI.getProtocol()) ? true : false),
                    response.statusCode(),
                    response.hasHeader("location"),
                    response.header("location"),
                    resourceCheckErrorMessage);
            else
                hyperLinksHealthStatus = new HyperLinksHealthStatus(hyperLinkUrl,
                    ("https".equals(hyperLinkURI.getProtocol()) ? true : false),
                    0,
                    false,
                    "",
                    resourceCheckErrorMessage);
        }

        return CompletableFuture.completedFuture(hyperLinksHealthStatus);
    }
}
