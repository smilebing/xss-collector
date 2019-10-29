package com.bing.xsscollector.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/")
public class IndexController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("/c")
    public String collect(String k, String v) {

        if (k == null && v == null) {
            return "k&v null";
        }

        String sql = "insert into xss_info (k,v) values(?,?)";
        jdbcTemplate.update(sql, new Object[]{k, v});

        return "ok";
    }

    @RequestMapping("/s")
    public String show(@RequestParam(required = true) Integer limit) {
        String sql = "select * from xss_info limit " + limit;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = null;
        try {
            s = objectMapper.writeValueAsString(maps);
        } catch (JsonProcessingException e) {
            return "error";
        }
        return s;
    }

}
