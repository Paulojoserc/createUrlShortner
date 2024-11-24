package com.rocketseat.createUrlShortner;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor

@Setter
@Getter
public class UrlData {
    private String originalUrl;
    private long expirationTime;

    public  UrlData(String originalUrl, long expirationTimeInSeconds) {
    }
}
