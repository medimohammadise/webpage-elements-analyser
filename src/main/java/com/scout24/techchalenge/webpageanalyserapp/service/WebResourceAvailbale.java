package com.scout24.techchalenge.webpageanalyserapp.service;

import com.scout24.techchalenge.webpageanalyserapp.service.dto.HyperLinksStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
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
public class WebResourceAvailbale {
    private final Logger log = LoggerFactory.getLogger(getClass());
    /*public static void main(String[] args){
        WebPageAnayserService webPageAnayserService=null;
        try {
            webPageAnayserService = new WebPageAnayserService("http://www.elizabethcastro.com/");
            webPageAnayserService.getHyperLinks ().parallelStream() .forEach(linkElement->{
                final String  url=linkElement.attr("href").startsWith("/")?"http://www.elizabethcastro.com"+linkElement.attr("href"):linkElement.attr("href");

                try {
                        //System.out.println("url : "+linkElement.attr("href"));
                        System.out.println("processing ... "+url);
                        CompletableFuture<Connection.Response> c=checkResource(url);
                        System.out.println("url : "+url+""+c.get().statusCode()+ c.get().hasHeader("location"));
                    } catch (Exception e) {
                        System.out.println("Exception for "+url);
                        //e.printStackTrace();
                    }
                }
               );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
    @GetMapping("/hyperLinksStatus")
    public Mono<List<HyperLinksStatus>> analuseHyperLinksStatus()  {

        WebPageAnayserService webPageAnayserService=null;

        try {
            webPageAnayserService = new WebPageAnayserService("http://www.elizabethcastro.com/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Flux.fromIterable(webPageAnayserService.getHyperLinks ().parallelStream().map(new Function<Element, HyperLinksStatus>() {
                                                                               @Override
                                                                               public HyperLinksStatus apply(Element element) {

                                                                                   try {
                                                                                       return checkResource(element).get();
                                                                                   } catch (InterruptedException e) {
                                                                                       e.printStackTrace();
                                                                                   } catch (ExecutionException e) {
                                                                                       e.printStackTrace();
                                                                                   }
                                                                                   return new HyperLinksStatus();
                                                                               }
                                                                           }







        ).collect(Collectors.toList())).collectList();
       // CompletableFuture.allOf(t.toArray(CompletableFuture<HyperLinksStatus>));


    }
    @Async
    public  CompletableFuture<HyperLinksStatus> checkResource(Element linkElement) {
        final String  url=linkElement.attr("href").startsWith("/")?"http://www.elizabethcastro.com"+linkElement.attr("href"):linkElement.attr("href");
        log.info("processing url "+url);
        Connection.Response response= null;
        String resourceCheckErrorMessage="";
        HyperLinksStatus hyperLinksStatus=null;
        try {
            response = Jsoup.connect(url).method(Connection.Method.GET).followRedirects(true).execute();
        } catch (Exception e) {
            resourceCheckErrorMessage=e.getMessage();
        }
        finally {
            if (response!=null)
                hyperLinksStatus=new HyperLinksStatus(url,
                    false,
                    response.statusCode(),
                    response.hasHeader("location"),
                    response.header("location"),
                    resourceCheckErrorMessage);
            else
                hyperLinksStatus=new HyperLinksStatus(url,
                    false,
                    0,
                    false,
                    "",
                    resourceCheckErrorMessage);
        }

        return CompletableFuture.completedFuture(hyperLinksStatus);
    }
}
