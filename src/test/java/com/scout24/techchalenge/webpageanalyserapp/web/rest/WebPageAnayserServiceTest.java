package com.scout24.techchalenge.webpageanalyserapp.web.rest;

import com.scout24.techchalenge.webpageanalyserapp.WebPageElementAnalyserApp;
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

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebPageElementAnalyserApp.class)
public class WebPageAnayserServiceTest {
    private static final String GITHUB_LOGIN_URL = "https://github.com/login";
    private MockMvc restUserMockMvc;

    @Autowired
    WebPageAnayserService webPageAnayserService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebPageAnalyseResource webPageAnalyseResource = new WebPageAnalyseResource(webPageAnayserService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(webPageAnalyseResource)
            /*.setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)*/
            .build();
    }

    @Test
    public void analyseWebPageWithLoginPage() throws Exception {
        restUserMockMvc.perform((get("/api/webPageMetaData")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url",GITHUB_LOGIN_URL)
             ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hasLoginForm",is(true)))
            .andExpect(jsonPath("$.htmlVersion",is("5")))
            ;
    }

}
