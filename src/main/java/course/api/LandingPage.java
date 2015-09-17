package course.api;

import course.domain.Course;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hashcode on 2015/09/17.
 */
@RestController
@RequestMapping("/")
public class LandingPage {
    @RequestMapping(method = RequestMethod.GET)
    public Course getCourse(){
        Course couse = new Course.Builder("12345")
                .name("Landing Page").offering(2015).build();
        return couse;
    }

}
