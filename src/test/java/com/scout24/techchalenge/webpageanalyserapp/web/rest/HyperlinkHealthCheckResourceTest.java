package com.scout24.techchalenge.webpageanalyserapp.web.rest;

import com.scout24.techchalenge.webpageanalyserapp.WebPageElementAnalyserApp;
import com.scout24.techchalenge.webpageanalyserapp.service.HyperLinkHealthCheckService;
import com.scout24.techchalenge.webpageanalyserapp.service.WebPageAnayserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebPageElementAnalyserApp.class)
public class HyperlinkHealthCheckResourceTest {
    private static final String GITHUB_LOGIN_URL = "https://github.com/login";
    private MockMvc restUserMockMvc;

    @Autowired
    HyperLinkHealthCheckService hyperLinkHealthCheckService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HyperLinkHealthCheckResource hyperLinkHealthCheckResource = new HyperLinkHealthCheckResource(hyperLinkHealthCheckService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(hyperLinkHealthCheckResource)
            /*.setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)*/
            .build();
    }

    @Test
    public void analyseWebPageWithLoginPage() throws Exception {
        restUserMockMvc.perform((get("/api/hyperLinksHealth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url",GITHUB_LOGIN_URL)
             ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(13)))

            ;
    }

}
