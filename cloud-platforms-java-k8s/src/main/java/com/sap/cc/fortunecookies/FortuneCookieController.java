package com.sap.cc.fortunecookies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FortuneCookieController.PATH)
public class FortuneCookieController {

    static final String PATH = "/";

    @Autowired
    private FortuneCookieRepository repo;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getQuote() {
        Pageable justOne = PageRequest.of(0, 1);
        return repo.getRandomQuotes(justOne).getContent().get(0).getQuote();
        //return decoratedQuote(repo.getRandomQuotes(justOne).getContent().get(0).getQuote());
    }

    private String decoratedQuote(String quote){
        int lenStr = quote.length();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i< lenStr + 2; i++){
            stringBuffer.append('*');
        }
        String s = stringBuffer.toString();
        stringBuffer.append("\n");
        stringBuffer.append(quote);
        stringBuffer.append("\n");
        stringBuffer.append(s);
        return stringBuffer.toString();
    }

}
