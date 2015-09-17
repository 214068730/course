package course.repository;

import course.domain.Course;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by hashcode on 2015/05/02.
 */
//@SpringApplicationConfiguration(classes= App.class)
//@WebAppConfiguration
public class TestNCrudCourse  extends AbstractTestNGSpringContextTests{
    private Long id;

    @Autowired
    CourseRepository repository;
//    @Test
    public void create() throws Exception {
        System.out.println( "Hello World");
        Course course = new Course.Builder("1222")
                .name("National Diploma IT").offering(2015).build();
        repository.save(course);
        id=course.getId();
        Assert.assertNotNull(course.getId());
    }

//    @Test(dependsOnMethods = "create")
    public void read() throws Exception {
        Course course = repository.findOne(id);
        Assert.assertEquals("National Diploma IT",course.getName());
    }

//    @Test(dependsOnMethods = "read")
    public void update() throws Exception {
        Course course = repository.findOne(id);
        Course newcourse = new Course.Builder("121").id(course.getId())
                .name("Diploma IT").offering(2014).build();
        repository.save(newcourse);

        Course updatedcourse = repository.findOne(id);

        Assert.assertEquals("Diploma IT", updatedcourse.getName());



    }

//    @Test(dependsOnMethods = "update")
    public void delete() throws Exception {
        Course course = repository.findOne(id);
        repository.delete(course);
        Course newcourse = repository.findOne(id);
        Assert.assertNull(newcourse);

    }
}
