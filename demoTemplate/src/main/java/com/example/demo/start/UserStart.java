package com.example.demo.start;

        import com.example.demo.model.User;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.ParameterizedTypeReference;
        import org.springframework.http.*;
        import org.springframework.stereotype.Component;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.client.RestTemplate;

        import javax.servlet.http.Cookie;
        import javax.websocket.Session;
        import java.nio.file.attribute.UserPrincipalLookupService;
        import java.util.*;
        import java.util.stream.Collectors;

@RestController
public class UserStart {

private final String url = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    static String result = "";

    public UserStart() {
        String sessionId = getAllUsers();
        headers.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        UserStart userStart = new UserStart();
        System.out.println(userStart.getAllUsers());

        String res =userStart.createUser().getBody();

        String res1 =userStart.updateUser().getBody();

       String res2= userStart.deleteUser(3L).getBody();

String rez = res+res1+res2;
        System.out.println(rez);
    }


    public String getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return String.join(";", Objects.requireNonNull(forEntity.getHeaders().get("set-cookie")));
    }

    public ResponseEntity<String> createUser() {
        User user = new User(3L,"James","Brown", (byte) 41);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public ResponseEntity<String> updateUser() {
        User user = new User(3L,"Thomas","Shelby", (byte) 27);
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, 3);
    }

    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url + "/"+id, HttpMethod.DELETE, entity, String.class);

    }

}