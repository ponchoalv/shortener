FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/shortener.jar /shortener/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/shortener/app.jar"]
