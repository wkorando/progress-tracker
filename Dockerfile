FROM openjdk:16-oraclelinux7

COPY target/*.jar /run

CMD ["java", "-jar", "/run/*.jar"] 