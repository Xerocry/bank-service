########build stage########

FROM maven:3.6.2-jdk-12 as maven_build

COPY pom.xml /home/app/
RUN mvn -B -f /home/app/pom.xml dependency:go-offline

COPY src /home/app/src
WORKDIR /home/app

RUN mvn -B install  -Dmaven.test.skip

########run stage########
FROM maven:3.6.2-jdk-12


COPY target/atm-service-0.0.1-SNAPSHOT.jar /opt/app/ATMEmulator-ATM.jar
#RUN java -jar /opt/app/ATMEmulator-ATM.jar --thin.classpath --thin.root=/opt/app > /opt/app/classpath
ENTRYPOINT ["java", "-jar", "/opt/app/ATMEmulator-ATM.jar"]

