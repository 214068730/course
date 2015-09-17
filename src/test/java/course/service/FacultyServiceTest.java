package course.service;

import course.conf.factory.ContactFactory;
import course.conf.factory.DepartmentFactory;
import course.conf.factory.FacultyFactory;
import course.domain.ContactAddress;
import course.domain.Department;
import course.domain.Faculty;
import course.repository.FacultyRepository;
import course.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hashcode on 2015/05/12.
 */
//@SpringApplicationConfiguration(classes= App.class)
//@WebAppConfiguration
public class FacultyServiceTest extends AbstractTestNGSpringContextTests{
    @Autowired
    private FacultyService service;

    private Long id;

    @Autowired
    private FacultyRepository repository;
    private List<Department> departments;
//    @BeforeMethod
    public void setUp() throws Exception {
        departments = new ArrayList<Department>();

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
                .createFaculty(facultyaddress, departments, "FID");

        repository.save(faculty);
        id=faculty.getId();
        Assert.assertNotNull(faculty.getId());
    }


//    @Test
    public void testGetFacultyDepartmets() throws Exception {
        List<Department> departments = service.getDepatments(id);
        Assert.assertTrue(departments.size() == 2);

    }
}
