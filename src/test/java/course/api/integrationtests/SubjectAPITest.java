package course.api.integrationtests;

import course.domain.Subject;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hashcode on 2015/08/21.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = App.class)
//@WebAppConfiguration
//@IntegrationTest({"server.port=8080"})
public class SubjectAPITest {

    final String BASE_URL = "http://localhost:8080/";
    private RestTemplate template;

//    @Before
    public void setUp() throws Exception {
        template = new TestRestTemplate();
    }

//    @Test
    public void testRead() throws Exception {
        ResponseEntity<String> response =template.getForEntity(BASE_URL+"api/subject/readall", String.class);

        System.out.println("The response is "+response.getBody());


    }

    public static final String REST_SERVICE_URI = "http://localhost:8080/api";

    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllSubjects(){
        System.out.println("Testing listAllSubjects API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> SubjectsMap = restTemplate.getForObject(REST_SERVICE_URI+"/subjects/", List.class);

        if(SubjectsMap!=null){
            for(LinkedHashMap<String, Object> map : SubjectsMap){
                System.out.println("Subject : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No Subject exist----------");
        }
    }

    /* GET */
    private static void getSubject(){
        System.out.println("Testing getSubject API----------");
        RestTemplate restTemplate = new RestTemplate();
        Subject Subject = restTemplate.getForObject(REST_SERVICE_URI+"/subject/1", Subject.class);
        System.out.println(Subject);
    }

    /* POST */
    private static void createSubject() {
        System.out.println("Testing create Subject API----------");
        RestTemplate restTemplate = new RestTemplate();
        Subject Subject = new Subject.Builder("100").name("TP3").build();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/subject/create/", Subject, Subject.class);
        System.out.println("Location : "+uri.toASCIIString());
    }

    /* PUT */
    private static void updateSubject() {
        System.out.println("Testing update Subject API----------");
        RestTemplate restTemplate = new RestTemplate();
        Subject Subject  =  new Subject.Builder("100").name("TP3").build();
        restTemplate.put(REST_SERVICE_URI+"/subject/update/1", Subject);
        System.out.println(Subject);
    }

    /* DELETE */
    private static void deleteSubject() {
        System.out.println("Testing delete Subject API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/subject/delete/3");
    }


    /* DELETE */
    private static void deleteAllSubjects() {
        System.out.println("Testing all delete Subjects API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/subject/");
    }

}
