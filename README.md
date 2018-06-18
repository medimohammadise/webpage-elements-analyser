# WebPageElementAnalyser
I built this application as code challenge solution. User enters a webpage url and application analyse elements inside webpage
## Development

I used following frameworks or libraries for building this application:

1. [Spring Boot Version 2.0][]: I exposed two rest API using spring boot rest features
2. [Angular Js Version 6.0 ]: I made the UI using angular .
3. [JSoup ]: For processing html
4. [PrimeNg ]: PrimeNg p-table component that supports pagination


 ## Running the project  
Just run the following command:
    ./mvnw
    
 the application would be available on 8080 port just go through this url: [http://localhost:8080/#/]

 ## Solution description

  I exposed two rest service for processing web page url:
   
 api/webPageMetaData?url={enter your web page url}
 api/hyperLinksHealth?url={enter your web page url}
 
 ### Performance consideration

 I separated into two webservice, because the second one for rich web pages (full of hyperlinks)
 is expected to take time.
 In both web services I used stream and parallelstream feature for java8 for facilitating processing and
 enhancing performance. In the second web service along with using lambda expression for concise code I used 
 @Async method for checking url health. I do not wanted a thread blocked for the reason that resource is not available.
 Please do check HyperLinkHealthCheckService code for more information.
 
  ### Login Form Checking
 
 Another thing that was tricky was how to check login form exists in the web page for this purpose I'm checking existence
 for password field along with other input text within form containing post actions.This solution is not 100% accurate.
 I did some extra action and I'm trying to login with any dummy user and password! For some web pages (github.com/login) I'm receiving "username and 
 password is not valid " and for other cases I'm facing other http response code. This one also was not 110% accurate trick for 
 checking login form availability but it was funny and sometimes it is working.
 
 ## Testing
   I have developed test class for back-end services using Junit and Spring Boot test features. 
 

## Deployment
   First of all You need to have yarn and npm installed in your pc.Go to the project's folder and then run yarn install to install
   required packages.
   
   For deployment you just need to build the war file first by using the following command :
     ./mvnw package -Pprod 
   war file would be ready after passing all tests.
   
   For building war file without runing test cases :
      ./mvnw package -Pprod -Dmaven.test.skip=true
      
   You can run the war file by simply using command java -jar web-page-element-analyser-0.0.1-SNAPSHOT.jar

