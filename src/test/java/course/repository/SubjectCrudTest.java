package course.repository;

import course.conf.factory.SubjectFactory;
import course.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

/**
 * Created by hashcode on 2015/05/07.
 */
//@SpringApplicationConfiguration(classes= App.class)
//@WebAppConfiguration
public class SubjectCrudTest extends AbstractTestNGSpringContextTests{
    private Long id;
    @Autowired
    private SubjectRepository repository;
//    @Test
    public void create() throws Exception {
        Subject subject = SubjectFactory
                .createSubject("TPG300S", "Technical Programming 3");
        repository.save(subject);
        id=subject.getId();
        Assert.assertNotNull(subject);
    }

//    @Test(dependsOnMethods = "create")
    public void read() throws Exception {
        Subject subject = repository.findOne(id);
        Assert.assertNotNull(subject);

    }

//    @Test(dependsOnMethods = "read")
    public void update() throws Exception {

    }

//    @Test(dependsOnMethods = "update")
    public void delete() throws Exception {
        Subject subject = repository.findOne(id);
        repository.delete(subject);
        Subject deletedsubject = repository.findOne(id);
        Assert.assertNull(deletedsubject);

    }
}
