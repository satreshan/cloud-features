package com.sap.cc.users;

import com.sap.cc.ascii.AsciiArtRequest;
import com.sap.cc.ascii.AsciiArtServiceClient;
import org.springframework.stereotype.Component;

@Component
public class PrettyUserPageCreator {

    private AsciiArtServiceClient asciiArtServiceClient;

    public PrettyUserPageCreator(AsciiArtServiceClient asciiArtServiceClient) {
        this.asciiArtServiceClient = asciiArtServiceClient;
    }

    public String getPrettyPage(User user) {
        AsciiArtRequest asciiArtRequest = new AsciiArtRequest(user.getName(), user.getFontPreference());
        return asciiArtServiceClient.getAsciiString(asciiArtRequest) + "\r\n" + user.getPhoneNumber();
    }
}