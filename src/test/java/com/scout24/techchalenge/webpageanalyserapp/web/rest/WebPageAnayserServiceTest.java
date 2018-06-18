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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebPageElementAnalyserApp.class)
public class WebPageAnayserServiceTest {
    private static final String GITHUB_LOGIN_URL = "https://github.com/login";
    private static final String MEINSPIEGEL_LOGIN_URL = "https://www.spiegel.de/meinspiegel/login.html";
    private static final String SCOUT24_WEB_SITE_HOME_PAGE = "https://www.scout24.com/en/Home.aspx";

    private MockMvc restUserMockMvc;

    @Autowired
    WebPageAnayserService webPageAnayserService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebPageAnalyseResource webPageAnalyseResource = new WebPageAnalyseResource(webPageAnayserService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(webPageAnalyseResource)
            .build();
    }

    @Test
    public void analyseWebPageWithGitHubLoginPage() throws Exception {
        restUserMockMvc.perform((get("/api/webPageMetaData")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url",GITHUB_LOGIN_URL)
             ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hasLoginForm",is(true)))
            .andExpect(jsonPath("$.htmlVersion",is("5")))
            ;
    }

    @Test
    public void analyseWebPageWithMeinSpiegelLoginPage() throws Exception {
        restUserMockMvc.perform((get("/api/webPageMetaData")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url",MEINSPIEGEL_LOGIN_URL)
        ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hasLoginForm",is(true)))
            .andExpect(jsonPath("$.htmlVersion",is("-//W3C//DTD HTML 4.01 Transitional//EN")))
        ;
    }
    @Test
    public void analyseWebPageWithScout24WebSiteHomePage() throws Exception {
        restUserMockMvc.perform((get("/api/webPageMetaData")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url",SCOUT24_WEB_SITE_HOME_PAGE)
        ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hasLoginForm",is(false)))
            .andExpect(jsonPath("$.htmlVersion",is("5")))
            .andExpect(jsonPath("$.numberOfExternalLinks",is(21)))
        ;
    }
}
