package com.cemiltokatli.main;

import com.cemiltokatli.jurl.*;
import com.cemiltokatli.jurl.exception.MalformedHttpURLException;

public class Main {
    public static void main(String[] args) throws MalformedHttpURLException{
            HttpURL s = JURL.build(Protocol.HTTP);

            String url = JURL.build(Protocol.HTTPS)
                    .fromString("http://www.domain.org/{a1}/{a2}")
                    .setRouteParam("a1", "foo")
                    .setRouteParam("a2", "New-York")
                    .toString();

            System.out.println(url);
    }
}
