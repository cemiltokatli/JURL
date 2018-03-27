# JURL - Java URL Builder

JURL is a Java URL builder library that allows you to create URLs with different schemes without dealing with string building or concatenation.

You can create URLs with different schemes for different protocols by using only setter methods, without dealing with string builders or concatenation operations.

JURL supports the following schemes:

* `http://`
* `https://`
* `file://`
* `ftp://`
* `ftps://`
* `sftp://`
* `data:`
* `telnet://`
* `mailto:`


## Installation

[![Bintray](https://img.shields.io/maven-central/v/io.github.cemiltokatli.jurl/java-url-builder.svg)](https://mvnrepository.com/artifact/io.github.cemiltokatli.jurl/java-url-builder)

JRUL is available as a downloadable `.jar` java library. You can download the JAR file and directly import to your project.
The current release version is 1.3.

* [Download **jurl-1.3.jar** core library](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3.jar)
* [Download **jurl-1.3-sources.jar** optional sources jar](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-sources.jar)
* [Download **jurl-1.3-javadoc.jar** optional javadoc jar](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc.jar)


### Maven
If you use Maven to manage the dependencies in your project, you do not need to download the jar file; just place the following code excerpt into your POM's `<dependencies>` section:

```
<dependency>
  <!-- JURL - Java URL Builder library @ https://github.com/cemiltokatli/JURL -->
  <groupId>io.github.cemiltokatli.jurl</groupId>
  <artifactId>java-url-builder</artifactId>
  <version>1.3</version>
</dependency>
```

### Building from source
If you want to build from source, it's best to use git so that you can stay up to date, and be able to contribute your changes back:

```
git clone https://github.com/cemiltokatli/JURL.git
cd JURL
mvn install
```

#### Tests
If you are building from source and make some changes, you might want to test your changes. JURL uses [JUnit](https://junit.org/junit5/) for testing and all test cases are located under the `src/test/` folder. The data prepared for testing purposes are located under the `src/test/resources` directory as [JSON](http://json.org/) files.

To run tests, you can use the `mvn test`command.


### Dependencies
JURL is entirely self contained and has no dependencies but for testing, it uses [JUnit](https://junit.org/junit5/) and [stleary's JSON library](https://github.com/stleary/JSON-java).

JURL runs on Java 9 or up.

## Usage
To start building a URL, you should use the static `build` method of the `JURL` class under the `com.cemiltokatli.jurl` package.
This method takes an argument of type `Protocol<T>`, which specifies the protocol of the URL that you want to create and returns an object of a class derived from the `URL` class.

For example:

```
import com.cemiltokatli.jurl.*;

HttpURL myUrl1 = JURL.build(Protocol.HTTP); //for http://
HttpURL myUrl3 = JURL.build(Protocol.HTTPS); //for https://
FileURL myUrl4 = JURL.build(Protocol.FTP); //for ftp://
FileURL myUrl5 = JURL.build(Protocol.SFTP); //for ftps://
DataURL myUrl6 = JURL.build(Protocol.DATA); //for data:
```

`Protocol` class has a static attribute for each type of protocol. [Click here to see the reference.](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc/com/cemiltokatli/jurl/Protocol.html)


#### HTTP URLs
An object of an `HttpURL` class represents an `http://` or `https://` URL. You can add route parameters, query fields, a host name or a fragment in the URL by using the methods of this class. [Click here to see the reference](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc/com/cemiltokatli/jurl/HttpURL.html).

```
String url = JURL.build(Protocol.HTTP)
                 .setHost("domain.com")
                 .toString();
System.out.println(url);
//http://domain.com
```

You can add route parameters in your URL.

```
String url = JURL.build(Protocol.HTTP)
                 .setHost("domain.com")
                 .addRouteParam("category")
                 .addRouteParam("page")
                 .addRouteParam("index.jsp")
                 .toString();
                         
System.out.println(url);
//http://domain.com/category/page/index.jsp
```        
        
It is also possible to build a URL from a given structure. In the following example, we give a URL string to the `setString` method which must be the first method called after the `build` method if you want to parse a URL and edit the URL with `addRouteParam` and `removeRouteParam` methods.

```
import com.cemiltokatli.jurl.*;
import com.cemiltokatli.jurl.exception.MalformedHttpURLException;

try {
     	String url = JURL.build(Protocol.HTTP)
                         .setString("domain.com/category/page/index.jsp")
                         .removeRouteParam("category")
                         .removeRouteParam("page")
                         .removeRouteParam("index.jsp")
                         .addRouteParam("my-category")
                         .addRouteParam("my-page")
                         .addRouteParam("index.jsp")
                         .toString();
       	System.out.println(url);
        //http://domain.com/my-category/my-page/index.jsp
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

_Attention: `setString` method throws a `MalformedHttpURLException` so you have to catch or throw it._

You can also add placeholders in the URL and then set route parameters.

```
try {
		String url = JURL.build(Protocol.HTTP)
                         .setString("domain.com/{param1}/{param2}/{param3}")
                         .setRouteParam("param1", "my-category")
                         .setRouteParam("param2", "my-page")
                         .setRouteParam("param3", "index.jsp")
                         .toString();
            System.out.println(url);
            //http://domain.com/my-category/my-page/index.jsp
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

Of course, you can assign the return value of the `build` method to a variable and use it later.

```
try {
	HttpURL url = JURL.build(Protocol.HTTP);
    url.setString("domain.com/{param1}/{param2}/{param3}");
    url.setRouteParam("param1", "my-category");
    url.setRouteParam("param2", "my-page");
    url.setRouteParam("param3", "index.jsp");

    System.out.println(url.toString());
    //http://domain.com/my-category/my-page/index.jsp
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

You can add or remove query fields and use the all of them together to manipulate a URL.

```
String url = JURL.build(Protocol.HTTP)
                 .setHost("domain.com")
                 .addRouteParam("test.jsp")
                 .addQueryField("id", "100")
                 .addQueryField("page", "2")
                 .toString();

System.out.println(url);
//http://domain.com/test.jsp?id=100&page=2
```
 
``` 
try {
	String url = JURL.build(Protocol.HTTP)
                     .setString("domain.com/?foo=bar&lang=en&page=1")
                     .addQueryField("id","100")
                     .removeQueryField("foo")
                     .removeQueryField("lang")
                     .removeQueryField("page")
                     .toString();

	System.out.println(url);
	//http://domain.com/?id=100
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

```
try {
	String url = JURL.build(Protocol.HTTP)
                     .setString("domain.com/foo/bar/{parameter}?page=100")
                     .removeQueryField("page")
                     .addQueryField("id", "1000")
                     .setRouteParam("parameter","test.jsp")
                     .removeRouteParam("bar")
                     .toString();

	System.out.println(url);
	//http://domain.com/foo/test.jsp?id=1000
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

You can add a fragment in your URL or remove the existing one.

```
String url = JURL.build(Protocol.HTTP)
                 .setHost("domain.com")
                 .setFragment("test")
                 .toString();
             
System.out.println(url);
//http://domain.com/#test
```

```
String url = JURL.build(Protocol.HTTP)
                  .setHost("domain.com")
                  .setFragment("test")
                  .addRouteParam("test.jsp")
                  .toString();
             
System.out.println(url);
//http://domain.com/test.jsp#test
```            

```
try {
	String url = JURL.build(Protocol.HTTP)
                     .setString("domain.com/test.jsp#test")
                     .clearFragment()
                     .toString();

	System.out.println(url);
	//http://domain.com/test.jsp
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

You can add or remove the `www` portion by giving a boolean argument to `showWWW` method. You can also add or remove a port number and change the host name.

```
try {
	String url = JURL.build(Protocol.HTTP)
                     .setString("www.domain.com/foo/bar/test.jsp")
                     .showWWW(false)
                     .setHost("mydomain.net")
                     .toString();

	System.out.println(url);
	//http://mydomain.net/foo/bar/test.jsp
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

```
try {
	String url = JURL.build(Protocol.HTTP)
                     .setString("www.domain.com/foo/bar/test.jsp")
                     .setHost("mydomain.net")
                     .toString();

    System.out.println(url);
    //http://www.mydomain.net/foo/bar/test.jsp
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

```
String url = JURL.build(Protocol.HTTP)
                 .setHost("100.200.10.20")
                 .setPort(20)
                 .toString();

System.out.println(url);
//http://100.200.10.20:20
```

```
try {
	String url = JURL.build(Protocol.HTTP)
                     .setString("100.200.10.20:20")
                     .setPort(-1)
                     .toString();

	System.out.println(url);
	//http://100.200.10.20
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

You can get the specific parts of a URL by using the getter methods.

```
try {
	HttpURL url = JURL.build(Protocol.HTTP)
                      .setString("www.domain.com/category/page/index.jsp?id=100&page=2#test");

	System.out.println("Protocol: "+url.getProtocol());
	System.out.println("Host Name : "+url.getHost());
	System.out.println("WWW: "+url.isWWWShown());
	System.out.println("Route Parameters: ");
	for(String routeParam : url.getRouteParams())
		System.out.println("\t"+routeParam);

	System.out.println("Query Fields: ");
	for(Map.Entry<String, String> field : url.getQueryFields().entrySet())
  		System.out.println("\t"+field.getKey()+" = "+field.getValue());

	System.out.println("Fragment: "+url.getFragment());
	System.out.println("Port: "+url.getPort());

	/*
	 * Output
      		Protocol: http://
            Host Name : domain.com
            WWW: true
            Route Parameters: 
                         category
                         page
                         index.jsp
            Query Fields: 
                         id = 100
                         page = 2
            Fragment: test
            Port: -1
      */
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

You can convert your URL to a string by using the `toString` method. Optionally, you can encode your URL by giving a `true` value as an argument to `toString ` method.

```
String url = JURL.build(Protocol.HTTP)
                 .setHost("domain.com")
                 .showWWW(true)
                 .addRouteParam("test 1")
                 .addRouteParam("test 2")
                 .addRouteParam("file test.html")
                 .setFragment("hello java")
                 .toString(true);

System.out.println(url);
//http://www.domain.com/test%201/test%202/file%20test.html#hello%20java
```

You can also convert your URL to a `java.net.URL` object. Encoding is possible as well.

```
try {
	HttpURL urlBuilder = JURL.build(Protocol.HTTP)
                             .setHost("domain.com")
                             .showWWW(true)
                             .addRouteParam("test 1")
                             .addRouteParam("test 2")
                             .addRouteParam("file test.html")
                             .setFragment("hello java");


	java.net.URL url1 = urlBuilder.toURL();
	java.net.URL url2 = urlBuilder.toURL(true);

	System.out.println(url1.toString()); //http://www.domain.com/test 1/test 2/file test.html#hello java
	System.out.println(url2.toString()); //http://www.domain.com/test%201/test%202/file%20test.html#hello%20java

}
catch(java.net.MalformedURLException e){
	e.printStackTrace();
}
```

More examples:

```
String url = JURL.build(Protocol.HTTPS)
             	  .setHost("domain.net.tr")
             	  .showWWW(true)
                  .addRouteParam("category-1")
                  .addRouteParam("page-1")
                  .addRouteParam("index.jsp")
                  .addQueryField("id", "100")
                  .addQueryField("page", "5")
                  .setFragment("test")
                  .toString();
                
System.out.println(url);
//https://www.domain.net.tr/category-1/page-1/index.jsp?id=100&page=5#test
```

```
String url = JURL.build(Protocol.HTTPS)
             	 .setHost("domain.com")
                 .showWWW(true)
                 .addQueryField("id", "100")
                 .setFragment("test")
                 .toString();
                
System.out.println(url);
//https://www.domain.com/?id=100#test
```

```
String url = JURL.build(Protocol.HTTPS)
                 .setHost("domain.com")
                 .addRouteParam("foo")
                 .setFragment("bar")
                 .toString();
                     
System.out.println(url);
//https://domain.com/foo/#bar
```

```
String url = JURL.build(Protocol.HTTPS)
                 .setHost("domain.com")
                 .toString();
                     
System.out.println(url);
//https://domain.com
```
 
```
String url = JURL.build(Protocol.HTTPS)
                 .setHost("domain.com")
                 .addQueryField("id","1")
                 .addRouteParam("index.jsp")
                 .toString();
                 
System.out.println(url);
//https://domain.com/index.jsp?id=1
```

```
try {
	String url = JURL.build(Protocol.HTTP)
                 	 .setString("https://www.domain.com/{prm1}/{prm2}/{file}")
                  	 .setRouteParam("prm1","foo")
                     .setRouteParam("prm2","bar")
                     .setRouteParam("file","index.jsp")
                     .setFragment("test")
                     .toString();

	System.out.println(url);
	//http://www.domain.com/foo/bar/index.jsp#test
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```        

```
try {
	String url = JURL.build(Protocol.HTTP)
                 	 .setString("100.200.50.60")
                     .addRouteParam("index.jsp")
                     .addQueryField("id","100")
                     .toString();

	System.out.println(url);
	//http://100.200.50.60/index.jsp?id=100
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

```
try {
	String url = JURL.build(Protocol.HTTP)
                  	 .setString("100.200.50.60/foo/bar/test/index.html#test")
                     .addRouteParam("index.jsp")
                     .removeRouteParam("index.html")
                     .removeRouteParam("test")
                     .removeRouteParam("foo")
                     .removeRouteParam("bar")
                     .setFragment("java")
                     .setPort(8080)
                     .toString();

	System.out.println(url);
	//http://100.200.50.60:8080/index.jsp#java
}
catch(MalformedHttpURLException e){
	e.printStackTrace();
}
```

#### File URLs
An object of a `FileURL` class represents a file URL that starts with a scheme like `file://`, `ftp://`, `sftp://` or `ftps://`. You can set a user name, password or a port number and add file path in your URL. As like the others, you can start building a file URL by calling the `build` method of the `JURL` class. [Click here to see the reference](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc/com/cemiltokatli/jurl/FileURL.html).

```
String url = JURL.build(Protocol.FTP)
                 .setHost("domain.com")
                 .setUsername("user1")
                 .setPassword("pass1")
                 .toString();

System.out.println(url);
//ftp://user1:pass1@domain.com
```

```
String url = JURL.build(Protocol.FTP)
                 .setHost("domain.com")
                 .setUsername("user1")
                 .setPassword("pass1")
                 .setPort(21)
                 .toString();

System.out.println(url);
//ftp://user1:pass1@domain.com:21
```

```
String url = JURL.build(Protocol.SFTP)
                 .setHost("100.200.20.30")
                 .setUsername("anonymous")
                 .setPort(21)
                 .toString();

System.out.println(url);
//sftp://anonymous@100.200.20.30:21
```

```
String url = JURL.build(Protocol.SFTP)
                 .setHost("100.200.20.30")
                 .setUsername("username")
                 .setPassword("password")
                 .addPathSegment("var")
                 .addPathSegment("www")
                 .addPathSegment("public_html")
                 .setPort(21)
                 .toString();

System.out.println(url);
//sftp://username:password@100.200.20.30:21/var/www/public_html/
```

You can convert your URL to a string by using the `toString` method and encode it by calling the `toString` method with a `true` value.

```
String url = JURL.build(Protocol.FTP)
                 .setHost("100.200.20.300")
                 .setUsername("setusr@domain.net")
                 .setPassword("jA$+by -?r0E@S_-")
                 .setPort(21)
                 .toString(true);

System.out.println(url);
//ftp://setusr%40domain.net:jA%24%2Bby%20-%3Fr0E%40S_-@100.200.20.300:21
```

#### Telnet URLs
An object of a `TelnetURL` class represents a `telnet://` URL. You can start building a telnet URL by calling the static `build` method of the `JURL` class. [Click here to see the reference.](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc/com/cemiltokatli/jurl/TelnetURL.html)

You can set a user name, password, host or a port number by using the setter methods. You can convert your URL to a string by using the `toString` method and encode it by calling the `toString` method with a `true` value.

```
TelnetURL url = JURL.build(Protocol.TELNET)
                    .setUsername("user")
                    .setPassword("pass $ foo")
                    .setHost("domain.com")
                    .setPort(80);

System.out.println(url.toString()); //telnet://user:pass $ foo@domain.com:80
System.out.println(url.toString(true)); //telnet://user:pass%20%24%20foo@domain.com:80
````
        
#### Data URLs
An object of a `DataURL` class represents a URL with a `data:` prefix. You can create a data URL by using the static `build` method of the `JURL` class. [Click here to see the reference.](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc/com/cemiltokatli/jurl/DataURL.html)

You can set your data and an optional media-type by using the setter methods of the class.

```
String url = JURL.build(Protocol.DATA)
             	 .setMediaType("text/plain")
                 .setData("Hello, Java!")
                 .toString();

System.out.println(url);
//data:text/plain,Hello, Java!
```

You can encode your data to base64 by calling the `setBase64` method with a `true` value.

```
String url = JURL.build(Protocol.DATA)
                 .setMediaType("text/html")
                 .setData("<b>Hello, Java!</b>")
                 .setBase64(true)
                 .toString();
                         
System.out.println(url);
//data:text/html;base64,PGI+SGVsbG8sIEphdmEhPC9iPg==
```

You can convert your URL to a string by using the `toString` method and encode it by calling the `toString` method with a `true` value.

```
String url = JURL.build(Protocol.DATA)
                 .setMediaType("text/html")
                 .setData("<b>Hello, Java!</b>")
                 .toString(true);
                         
System.out.println(url);
//data:text/html,%3Cb%3EHello%2C%20Java!%3C%2Fb%3E
```

#### Mailto URLs
An object of a `MailtoURL` class represents a URL with a `mailto:` prefix. You can create a mail URL by using the static `build` method of the `JURL` class. [Click here to see the reference.](http://cemiltokatli.com/libs/jurl/java-url-builder-1.3-javadoc/com/cemiltokatli/jurl/MailtoURL.html)

You can set an e-mail address by using the `setEmailAddress` method.

```
String url = JURL.build(Protocol.MAILTO)
                 .setEmailAddress("test@domain.com")
                 .toString();
                         
System.out.println(url);
//mailto:test@domain.com
```

You can add a subject or content in the URL by using the `setSubject` and `setContent` methods.

```
String url = JURL.build(Protocol.MAILTO)
                 .setEmailAddress("test@domain.com")
                 .setSubject("Foo")
                 .toString();

System.out.println(url);
//mailto:test@domain.com?subject=Foo
```

```
String url = JURL.build(Protocol.MAILTO)
                 .setEmailAddress("test@domain.com")
                 .setSubject("Foo")
                 .setContent("Bar")
                 .toString();
                         
System.out.println(url);
//mailto:test@domain.com?subject=Foo&body=Bar
```

You can convert your URL to a string by using the `toString` method and encode it by calling the `toString` method with a `true` value.

```
String url = JURL.build(Protocol.MAILTO)
                 .setEmailAddress("test@domain.com")
                 .setSubject("Test Subject")
                 .setContent("Test Content")
                 .toString(true);
                         
System.out.println(url);
//mailto:test@domain.com?subject=Test%20Subject&body=Test%20Content
```

