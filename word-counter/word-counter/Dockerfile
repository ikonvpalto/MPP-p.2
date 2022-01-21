FROM tomcat:9-jdk17-temurin

COPY ./src/main/WEB-INF /usr/local/tomcat/webapps/ROOT/WEB-INF
COPY ./target/classes /usr/local/tomcat/webapps/ROOT/WEB-INF/classes
COPY ./src/main/jsp/* /usr/local/tomcat/webapps/ROOT/

EXPOSE 8080
CMD ["catalina.sh", "run"]