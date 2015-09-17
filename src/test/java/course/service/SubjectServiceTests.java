package course.service;

import course.App;
import course.conf.factory.SubjectFactory;
import course.domain.Subject;
import course.services.SubjectService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by hashcode on 2015/05/02.
 */
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class SubjectServiceTests extends AbstractTestNGSpringContextTests {
    private Long id;

    @Autowired
    private SubjectService service;

//    @Test
    public void create() throws Exception {
        //Create a Subject Class
        Subject subject = SubjectFactory
                .createSubject("TPG300S", "Technical Programming 3");
        // Save in the Database
        service.save(subject);
        //Id Should be available
        id = subject.getId();
        Assert.assertNotNull(subject);

    }

//    @Test(dependsOnMethods = "create")
    public void read() throws Exception {
        // Get subject
        Subject subject = service.findById(id);
        Assert.assertEquals("TPG300S", subject.getCode());
    }

//    @Test(dependsOnMethods = "read")
    public void update() throws Exception {
        //Get Subject
        Subject subject = service.findById(id);
        //Change it
        Subject newsubject = new Subject
                .Builder(subject.getCode())
                .copy(subject)
                .name("Technical").build();
        //Save it
        service.update(newsubject);
        //Get Updated Subject
        Subject updatedSubject = service.findById(id);
        Assert.assertEquals("Technical", updatedSubject.getName());

    }

//    @Test(dependsOnMethods = "update")
    public void delete() throws Exception {
        Subject subject = service.findById(id);
        service.delete(subject);
        Subject deletedSubject = service.findById(id);
        Assert.assertNull(deletedSubject);
    }
}
