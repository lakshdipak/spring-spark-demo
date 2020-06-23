package com.genesis.api.spark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("api")
@RestController
public class SparkController {
    @Autowired
    WordCountService service;

    @Autowired
    WordCount wordCount;

    @RequestMapping(method = RequestMethod.POST, path = "/wordcount")
    public Map<String, Long> count(@RequestParam(required = true) String words) {
        List<String> wordList = Arrays.asList(words.split("\\|"));
        return service.getCount(wordList);
    }

    @RequestMapping("wordcount")
    public ResponseEntity<List<Count>> words() {
        return new ResponseEntity<>(wordCount.count(), HttpStatus.OK);
    }


}
