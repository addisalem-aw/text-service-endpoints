package com.galvanize.textservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
public class TextServiceController {

    @GetMapping("/camelize")
    public String convertFromSnakeToCamel(@RequestParam String original, @RequestParam Optional<Boolean> initialCap) {
        StringBuilder camel=new StringBuilder();
        String[] split = original.split("_");
        for (int i = 0; i < split.length; i++) {
            String s=split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
            camel.append(s);
        }
        if(!(initialCap.isPresent())){
            String s=camel.substring(0, 1).toLowerCase()+ camel.substring(1);
            return s;
        }
        return camel.toString();
    }
    @GetMapping("/redact")
    public String hello(@RequestParam String original,@RequestParam List<String> badWord){
        StringBuilder newOrginal=new StringBuilder();
        newOrginal.append(original);
        for(String bad:badWord) {
            int l=bad.length();
            String repeated = new String(new char[l]).replace("\0", "*");
            newOrginal=replaceAll(newOrginal,bad,repeated);
        }
        return newOrginal.toString();
    }
    public static StringBuilder replaceAll(StringBuilder sb, String find, String replace){
        return new StringBuilder(Pattern.compile(find).matcher(sb).replaceAll(replace));
    }


}
