package course.api.mocktests;

import course.api.HomePage;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by hashcode on 2015/08/22.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = App.class)
//@ContextConfiguration(classes = MockServletContext.class)
//@WebAppConfiguration
public class HometMockTests{
    final String BASE_URL = "http://localhost:8080/";
    private MockMvc mockMvc;
//    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new HomePage())
                .build();

    }

//    @Test
    public void read() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(BASE_URL + "api/home")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        is("This is a Home Page")));
    }


}
