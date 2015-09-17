package course.repository;

import course.App;
import course.conf.factory.ContactFactory;
import course.conf.factory.DepartmentFactory;
import course.conf.factory.FacultyFactory;
import course.domain.ContactAddress;
import course.domain.Department;
import course.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hashcode on 2015/05/03.
 */
@SpringApplicationConfiguration(classes= App.class)
@WebAppConfiguration
public class FacultyCrudTest extends AbstractTestNGSpringContextTests{

    private Long id;

    private List<Department> departments;

    @Autowired
    private FacultyRepository repository;

    @BeforeMethod
    public void setUp() throws Exception {
         departments = new ArrayList<Department>();

    }

    @AfterMethod
    public void tearDown() throws Exception {
//        repository.deleteAll();

    }

//    @Test
    public void create() throws Exception {

        ContactAddress deptaddress = ContactFactory
                .createContact("dept@test.com", "7550");
        Department it = DepartmentFactory
                .createDepartment(deptaddress, "IT", null);
        Department arts = DepartmentFactory
                .createDepartment(deptaddress, "ART", null);

        ContactAddress facultyaddress = ContactFactory
                .createContact("faculty@test.com", "7550");


        departments.add(it);
        departments.add(arts);

        Faculty faculty = FacultyFactory
                .createFaculty(facultyaddress,departments,"FID");

        repository.save(faculty);
        id=faculty.getId();
        Assert.assertNotNull(faculty.getId());
    }

//    @Test(dependsOnMethods = "create")
    public void read() throws Exception {
        Faculty faculty = repository.findOne(id);
        Assert.assertNotNull(faculty.getId());
        Assert.assertEquals("7550", faculty.getAddress().getPostalCode());
        Assert.assertTrue(faculty.getDepartments().size() == 2);

    }

//    @Test(dependsOnMethods = "read")
    public void update() throws Exception {

    }

//    @Test(dependsOnMethods = "update")
    public void delete() throws Exception {
        Faculty faculty = repository.findOne(id);
        repository.delete(faculty);
        Faculty deletedFaculty = repository.findOne(id);
        Assert.assertNull(deletedFaculty);

    }


}
