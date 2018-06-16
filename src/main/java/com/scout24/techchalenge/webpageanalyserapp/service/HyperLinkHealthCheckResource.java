package com.scout24.techchalenge.webpageanalyserapp.service;

import com.scout24.techchalenge.webpageanalyserapp.service.dto.HyperLinksHealthStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class HyperLinkHealthCheckResource {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/hyperLinksHealth")
    public Mono<List<HyperLinksHealthStatus>> analuseHyperLinksStatus(@RequestParam String url) {

        WebPageAnayserService webPageAnayserService = null;


            webPageAnayserService = new WebPageAnayserService();

        try {
            webPageAnayserService.coonectToDocument(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Flux.fromIterable(webPageAnayserService.getHyperLinks().parallelStream().map(new Function<Element, HyperLinksHealthStatus>() {
                                                                                                @Override
                                                                                                public HyperLinksHealthStatus apply(Element element) {

                                                                                                    try {
                                                                                                        return checkResource(url, element.absUrl("href")).get();
                                                                                                    } catch (InterruptedException e) {
                                                                                                        e.printStackTrace();
                                                                                                    } catch (ExecutionException e) {
                                                                                                        e.printStackTrace();
                                                                                                    }
                                                                                                    return new HyperLinksHealthStatus();
                                                                                                }
                                                                                            }


        ).collect(Collectors.toList())).collectList();
        // CompletableFuture.allOf(t.toArray(CompletableFuture<HyperLinksHealthStatus>));


    }

    @Async
    public CompletableFuture<HyperLinksHealthStatus> checkResource(String url, String hyperLinkUrl) {
        hyperLinkUrl = hyperLinkUrl.replace("../", "");  //remove relative addresss part because we are extracting absolutePath
        log.info("processing url " + hyperLinkUrl);
        Connection.Response response = null;
        String resourceCheckErrorMessage = "";
        HyperLinksHealthStatus hyperLinksHealthStatus = null;
        try {
            response = Jsoup.connect(hyperLinkUrl).method(Connection.Method.GET).followRedirects(true).execute();
        } catch (Exception e) {
            resourceCheckErrorMessage = e.getMessage();
        } finally {
            if (response != null)
                hyperLinksHealthStatus = new HyperLinksHealthStatus(hyperLinkUrl,
                    false,
                    response.statusCode(),
                    response.hasHeader("location"),
                    response.header("location"),
                    resourceCheckErrorMessage);
            else
                hyperLinksHealthStatus = new HyperLinksHealthStatus(hyperLinkUrl,
                    false,
                    0,
                    false,
                    "",
                    resourceCheckErrorMessage);
        }

        return CompletableFuture.completedFuture(hyperLinksHealthStatus);
    }
}
