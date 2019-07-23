package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {

    public static final String API_ROOT = "https://api.predic8.de:443/shop";

    @Test
    public void getCategories() {
        String apiUrl = API_ROOT + "/categories/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void getCustomers() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void createCustomer() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Joe");
        postMap.put("lastname", "Buck");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void updateCustomer() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Michael");
        postMap.put("lastname", "Weston");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Michael 2");
        postMap.put("lastname", "Weston 2");
        restTemplate.put(apiUrl + id, postMap);

        jsonNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }


    @Test(expected = ResourceAccessException.class)
    public void updateCustomerUsingPatchSunHttp() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");
        jsonNode = restTemplate.patchForObject(apiUrl + id, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void updateCustomerUsingPatchApacheHttp() {
        String apiUrl = API_ROOT + "/customers/";
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");
        jsonNode = restTemplate.patchForObject(apiUrl + id, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() {
        String apiUrl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Michael");
        postMap.put("lastname", "Weston");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);

        restTemplate.delete(apiUrl + id);
        System.out.println("Deleted customer id: " + id);

        restTemplate.getForObject(apiUrl + id, JsonNode.class);
    }
}
