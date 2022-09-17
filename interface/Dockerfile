FROM hub.c.163.com/library/java:8-alpine

MAINTAINER ww5869@126.com

ADD youqiancheng-0.0.1-SNAPSHOT.jar app.jar

ADD alipayCertPublicKey_RSA2.crt /www/server/youqiancheng/alicert/
ADD appCertPublicKey_2021001163690789.crt /www/server/youqiancheng/alicert/
ADD alipayRootCert.crt /www/server/youqiancheng/alicert/
ADD violentVocabulary.txt /www/server/youqiancheng/txt/

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]
