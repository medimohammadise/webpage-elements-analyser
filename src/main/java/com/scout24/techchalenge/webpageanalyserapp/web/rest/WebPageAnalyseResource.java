package com.scout24.techchalenge.webpageanalyserapp.web.rest;

import com.scout24.techchalenge.webpageanalyserapp.service.WebPageAnayserService;
import com.scout24.techchalenge.webpageanalyserapp.service.dto.WebPageDocumentMetaDataDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class WebPageAnalyseResource {
    WebPageAnayserService webPageAnayserService;
    public WebPageAnalyseResource(WebPageAnayserService webPageAnayserService){
        this.webPageAnayserService=webPageAnayserService;
    }
    @GetMapping("/webPageMetaData")
    public WebPageDocumentMetaDataDTO analyseWebPage(@RequestParam String url) throws IOException {
        return webPageAnayserService.analyseWebPage(url);

    }
}
