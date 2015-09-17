package course.api.mocktests;

import course.api.CrudPageMockExample;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hashcode on 2015/08/22.
 */
public class CrudMockPageTest {
    final String BASE_URL = "http://localhost:8080/";
    private MockMvc mockMvc;

//    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(new CrudPageMockExample())
                .build();
    }

//    @Test
    public void read() throws Exception {

        mockMvc
                .perform(get(BASE_URL+"api/test/read/{id}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"name\":\"john\",\"age\":43}"));
    }

//    @Test
    public void readAll() throws Exception {

        mockMvc
                .perform(get(BASE_URL+"api/test/readall"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().string(
                                "{\"lecturers\":[{\"name\":\"john\",\"age\":43},"
                                        + "{\"name\":\"peter\",\"age\":40}]}"));
    }

//    @Test
    public void create() throws Exception {

        mockMvc
                .perform(
                        post(BASE_URL+"api/test/create").contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"mary\",\"age\":30}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().string("{\"name\":\"mary\",\"age\":30}"));
    }

//    @Test
    public void update() throws Exception {

        mockMvc
                .perform(
                        put(BASE_URL+"api/test/update").contentType(MediaType.APPLICATION_JSON).content(
                                "{\"name\":\"mary\",\"age\":10}")).andExpect(
                status().isOk());
    }

//    @Test
    public void remove() throws Exception {

        mockMvc
                .perform(
                        delete(BASE_URL+"api/test/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
