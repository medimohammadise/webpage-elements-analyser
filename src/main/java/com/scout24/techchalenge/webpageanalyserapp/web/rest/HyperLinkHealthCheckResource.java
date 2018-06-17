package com.scout24.techchalenge.webpageanalyserapp.web.rest;

import com.scout24.techchalenge.webpageanalyserapp.service.HyperLinkHealthCheckService;
import com.scout24.techchalenge.webpageanalyserapp.service.dto.HyperLinksHealthStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HyperLinkHealthCheckResource {
    HyperLinkHealthCheckService hyperLinkHealthCheckService;
    public HyperLinkHealthCheckResource(HyperLinkHealthCheckService hyperLinkHealthCheckService){
        this.hyperLinkHealthCheckService= hyperLinkHealthCheckService;
    }

    @GetMapping("/hyperLinksHealth")
    public List<HyperLinksHealthStatus> analyseHyperLinksStatus(@RequestParam String url) {
        return hyperLinkHealthCheckService.analyseHyperLinksStatus(url);
    }
}
