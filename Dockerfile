FROM openjdk:11

ADD fl-master-1.0.0.jar /

CMD ["java", "-jar", "fl-master-1.0.0.jar"]
