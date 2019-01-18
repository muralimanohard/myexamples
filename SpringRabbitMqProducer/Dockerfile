FROM openjdk:8
ADD target/rabbitmq_producer.jar rabbitmq_producer.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "rabbitmq_producer.jar"]