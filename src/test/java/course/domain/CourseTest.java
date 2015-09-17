package course.domain;

import course.conf.factory.CourseFactory;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hashcode on 2015/04/13.
 */
public class CourseTest {
    @Before
    public void setUp() throws Exception {


    }

//    @Test
    public void testCreateCourse() throws Exception {
        List<Subject> lecturers = new ArrayList<Subject>();
        Map<String,String> values = new HashMap<String,String>();

        values.put("code","NDP2015");
        values.put("name", "National Diploma");

        Course course =CourseFactory
                .createCourse(25, values, lecturers);

        Assert.assertEquals("NDP2017",course.getCode());
    }

//    @Test
    public void testUpadteCourse() throws Exception {
        List<Subject> lecturers = new ArrayList<Subject>();
        Map<String,String> values = new HashMap<String,String>();

        values.put("code","NDP2015");
        values.put("name", "National Diploma");

        Course course =CourseFactory
                .createCourse(25, values, lecturers);

        Course newcourse = new Course
                .Builder(course.getCode())
                .copy(course)
                .offering(20).build();

        Assert.assertEquals("NDP2015",newcourse.getCode());
        Assert.assertEquals("National Diploma",newcourse.getName());
        Assert.assertEquals(20,newcourse.getOffering());
        Assert.assertEquals(25,course.getOffering());


    }
}
