package com.catfish;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by A on 2017/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml", "classpath:spring-applicationContext.xml"})
@WebAppConfiguration
public class BaseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public String getForm(String url, Map<String, String> params) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(url);
        return getResult(requestForm(mockHttpServletRequestBuilder, params));
    }

    public ResultActions requestForm(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, Map<String, String> params) throws Exception {
        mockHttpServletRequestBuilder
                .accept(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding("UTF-8");
        Set<String> set = params.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = params.get(key);
            mockHttpServletRequestBuilder.param(key, value);
        }
        ResultActions ra = this.mockMvc
                .perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        return ra;
    }

    public String getResult(ResultActions ra) throws Exception {
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        return result;
    }

}
