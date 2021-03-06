# Fatura Onay Servisi

## Derle

Uygulama şu şekilde derlenir: <br>
`mvn clean install`

## Çalıştır

Derlendikten sonra şu şekilde çalıştırılabilir: <br>
`java -jar .\target\invoice-approver-0.0.1.jar`<br><br>
Yada Spring Boot plugini kullanılarak Uygulama ayağa kaldırılabilir: <br>
`mvn spring-boot:run`

## Dockerize Et

Derledikten sonra pom.xml'in olduğu kalsörde şu komut çalıştırılabilir: <br>
`docker build . -t invoice-approver:v.0.0.1`

### Ve Çalıştır

`docker run -d -p 8080:8080 invoice-approver:v.0.0.1`

## Test Et

Uygulama gömülü olarak H2 veritabanı ile ayağa kalkar ve 8080 portundan yayın yapmaya başlar.<br><br>
Yeni bir fatura ekle: <br>
`curl --location --request POST 'http://localhost:8080/invoice'
--header 'Content-Type: application/json' --data-raw '{
"purchasingSpecialist": {
"firstName": "John",
"lastName": "Doe",
"email": "john@doe.com"
},
"amount": 199,
"productName": "USB Disc",
"billNo": "TR002"
}'` <br><br>
Onaylanmış faturaları listele: <br>
`curl --location --request GET 'http://localhost:8080/invoice/approved'` <br><br>
Onaylanmamış faturaları listele: <br>
`curl --location --request GET 'http://localhost:8080/invoice/non-approved'` <br><br>
