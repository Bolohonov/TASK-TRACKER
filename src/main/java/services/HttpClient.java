//package services;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class HttpClient {
//
//    public void run() {
//        HttpClient client = HttpClient.newHttpClient();
//
//    }
//
//    // укажите URL запроса, включая его параметры
//    URI url = URI.create("https://api.exchangerate.host/convert/?base=RUB?symbols=USD,EUR");
//
//    // создайте объект, описывающий запрос с необходимой информацией
//    HttpRequest request = HttpRequest
//            .newBuilder()
//            .uri(url)
//            .GET()
//            .header("Acept", "aplication/json")
//            .build();
//
//        try {
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("Код статуса: " + response.statusCode());
//        System.out.println("Ответ: " + response.body());
//    } catch (IOException | InterruptedException e) { // обработка ошибки отправки запроса
//        System.out.println("Во время выполнения запроса ресурса по URL-адресу: '" + url + "' возникла ошибка.\n" +
//                "Проверьте, пожалуйста, адрес и повторите попытку.");
//    }
//}
