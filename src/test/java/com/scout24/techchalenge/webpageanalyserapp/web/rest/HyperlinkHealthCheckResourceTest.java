package com.scout24.techchalenge.webpageanalyserapp.web.rest;

import com.scout24.techchalenge.webpageanalyserapp.WebPageElementAnalyserApp;
import com.scout24.techchalenge.webpageanalyserapp.service.HyperLinkHealthCheckService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebPageElementAnalyserApp.class)
public class HyperlinkHealthCheckResourceTest {
    private static final String GITHUB_LOGIN_URL = "https://github.com/login";
    private static final String MEINSPIEGEL_LOGIN_URL = "https://www.spiegel.de/meinspiegel/login.html";
    private static final String SCOUT24_WEB_SITE_HOME_PAGE = "https://www.scout24.com/en/Home.aspx";

    private MockMvc restUserMockMvc;

    @Autowired
    HyperLinkHealthCheckService hyperLinkHealthCheckService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HyperLinkHealthCheckResource hyperLinkHealthCheckResource = new HyperLinkHealthCheckResource(hyperLinkHealthCheckService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(hyperLinkHealthCheckResource)
            .build();
    }

    @Test
    public void analyseWebPageWithGitHubLoginPage() throws Exception {
        restUserMockMvc.perform((get("/api/hyperLinksHealth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url", GITHUB_LOGIN_URL)
        ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(13)))

        ;
    }

    @Test
    public void analyseWebPageWithMeinSpiegelPage() throws Exception {
        restUserMockMvc.perform((get("/api/hyperLinksHealth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url", MEINSPIEGEL_LOGIN_URL)
        ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(265)));
    }

    @Test
    public void analyseWebPageWithScout24WebSiteHomePage() throws Exception {
        restUserMockMvc.perform((get("/api/hyperLinksHealth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .param("url", SCOUT24_WEB_SITE_HOME_PAGE)
        ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(132)));
    }
}
