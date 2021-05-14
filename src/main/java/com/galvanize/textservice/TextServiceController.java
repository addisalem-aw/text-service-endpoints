package com.galvanize.textservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

@RestController
public class TextServiceController {
    public static StringBuilder replaceAll(StringBuilder sb, String find, String replace){
        return new StringBuilder(Pattern.compile(find).matcher(sb).replaceAll(replace));
    }

    //////Endpoint 1 - Camelize
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

    ////Endpoint 2 - Redact
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

    //Endpoint 3 - Encode////////
    @PostMapping("/encode")
    public String messageEncoder(@RequestParam String message, @RequestParam String key){
        StringBuilder resultString = new StringBuilder();
        if(key!= null && !key.isEmpty() && key.length() ==26){
            char[] mappingSource = "abcdefghijklmnopqrstuvwzyz".toCharArray();
            HashMap<Character, Character> map = new HashMap<>();
            for(int i =0; i<mappingSource.length; i++){
                map.put(mappingSource[i], key.charAt(i) );
            }
            for(char c:message.toCharArray()){
                if(c==' ')
                    resultString.append(' ');
                else
                    resultString.append(map.get(c));
            }
        }

        return resultString.toString();
    }
    ///	//Endpoint 4 - find and replace
    @PostMapping(value = "/s/{find}/{replace}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String findAndReplace(String text,@PathVariable String find, @PathVariable String replace){
        StringBuilder newText=new StringBuilder(text);
        newText=replaceAll(newText,find,replace);
        return newText.toString();
    }

}
