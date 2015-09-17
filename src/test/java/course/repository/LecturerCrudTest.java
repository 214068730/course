package course.repository;

import course.App;
import course.conf.factory.LecturerFactory;
import course.domain.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hashcode on 2015/05/03.
 */
@SpringApplicationConfiguration(classes= App.class)
@WebAppConfiguration
public class LecturerCrudTest extends AbstractTestNGSpringContextTests{
    private Long id;

    @Autowired
    private LecturerRepository repository;

//    @Test
    public void create() throws Exception {
        Map<String,String> values = new HashMap<String,String>();
        values.put("firstNAme","John");
        values.put("lastName", "Doe");
        Lecturer lecturer = LecturerFactory
                .createLacturer(values,27);

        repository.save(lecturer);
        id=lecturer.getId();
        Assert.assertNotNull(lecturer.getId());

    }

//    @Test(dependsOnMethods = "create")
    public void read() throws Exception {
        Lecturer lecturer = repository.findOne(id);
        Assert.assertNotNull(lecturer);


    }

//    @Test(dependsOnMethods = "read")
    public void update() throws Exception {
        Lecturer lecturer = repository.findOne(id);

        Lecturer newlewcturer = new Lecturer
                .Builder(lecturer.getLastName())
                .copy(lecturer)
                .age(40)
                .build();

        repository.save(newlewcturer);

        Lecturer updatedLecturer = repository.findOne(id);
        Assert.assertEquals(updatedLecturer.getAge(),40);

    }

//    @Test(dependsOnMethods = "update")
    public void delete() throws Exception {
        Lecturer lecturer = repository.findOne(id);
        repository.delete(lecturer);
        Lecturer deletedLecturer = repository.findOne(id);
        Assert.assertNull(deletedLecturer);

    }

}
