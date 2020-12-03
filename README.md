# Getting Started

### Reference Documentation
This application based on JAVA Spring Boot with Maven, log4j for logging.. 
Aim of this app, takes .mp4 files from openshift zalenium volume, sends to external storage using rest services. 
Getting .mp4 files from /home/seluser/videos, putting into videos folder in my storage.
However, you need another service that runs in your server to upload a file into server storage. 


Explanation of Key Points: 

    * application.yml --> 
            $ocstorage.ocdirectory = Zalenium video folder in openshift connected a persistent volume
            $storage-file-app.url = This is a rest service that uploads the file into my storage.
            
    * Controller --> 2 methods 1) copyFile 2)copyAllVideos
        1) copyFile --> gets a file name from user and copy only this video file to storage
        2) copyAllVideos --> finds all .mp4 files in volume and send all of them into storage
        
        Both methods delete every file after, uploading.
        
    * Project runs in kubernates and you can see the yaml files and dockerfile.
      Modify them for your kubernates environment.

You can check the references in below.
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Zalenium](https://opensource.zalando.com/zalenium/)


You can clone this project and modify it for your own environment.

