FROM tomcat:9.0

# copy the packaged war file into webapps directory of tomcat
COPY target/RestAPI.war /usr/local/tomcat/webapps/

# set the startup command to execute the tomcat server
CMD ["catalina.sh", "run"]