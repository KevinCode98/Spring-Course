package pvin.curso.springboot.springbootjparelationship;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import pvin.curso.springboot.springbootjparelationship.entities.Client;
import pvin.curso.springboot.springbootjparelationship.entities.ClientDetails;
import pvin.curso.springboot.springbootjparelationship.entities.Course;
import pvin.curso.springboot.springbootjparelationship.entities.Student;
import pvin.curso.springboot.springbootjparelationship.repositories.ClientDetailsRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.ClientRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.CoursesRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.StudentRepository;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.show-sql = true",
        "spring.jpa.properties.hibernate.format_sql = true"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringbootJpaRelationshipApplicationTests {
    @Autowired
    private EntityManager em;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Test
    void saveStudentWithoutId() {
        System.out.println("---------------  START TEST  ---------------");
        // Manera estandar de almacenar un usuario -> Se encarga de todo
        Client kevinClient = new Client("Kevin", "Carrillo");
        clientRepository.save(kevinClient);

        // Con el uso persist se obtiene un valor nulo en el ID
        Client sergioClient = new Client("Sergio", "Malek");
        em.persist(sergioClient);
        System.out.println("clientPersist.getId() = " + sergioClient.getId());

        // Con el  uso de merge retorna una copia del objeto ya almacenada dentro del sistema
        Client alanClient = new Client("Alan", "Zaragoza");
        Client alanClientDBMerge = em.merge(alanClient);
        System.out.println("secondClient.getId() = " + alanClient.getId());
        System.out.println("mergedClient.getId() = " + alanClientDBMerge.getId());
        System.out.println("(secondClient == mergedClient) = " + (alanClient == alanClientDBMerge));

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveStudentWithId() {
        System.out.println("---------------  START TEST  ---------------");
//        Client kevinClient = new Client("Kevin", "Carrillo");
//        firstClient.setId(1L);
//        clientRepository.save(firstClient);
//
//        Client sergioClient = new Client("Sergio", "Malek");
//        clientPersist.setId(2L);
//        em.persist(clientPersist);
//
//        Client alanClient = new Client("Alan", "Zaragoza");
//        alanClient.setId(3L);
//        em.merge(mergeClient);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveStudentWithExistingId() {
        System.out.println("---------------  START TEST  ---------------");
//        Client kevinClient = new Client("Kevin", "Carrillo");
//        Client kevinClientDBSaved = clientRepository.save(kevinClient);
//        System.out.println("kevinClientDBSaved.getId() = " + kevinClientDBSaved.getId());
//
//        Client kevinClientFirstTrySave = new Client("Kevin", "Carrillo");
//        kevinClientFirstTrySave.setId(kevinClientDBSaved.getId());
//        clientRepository.save(kevinClientFirstTrySave);
//
//        Client kevinClientSecondTryPersist = new Client("Kevin", "Carrillo");
//        kevinClientSecondTryPersist.setId(kevinClientDBSaved.getId());
//        em.persist(kevinClientSecondTryPersist);
//
//        Client kevinClientThirdTryMerge = new Client("Kevin", "Carrillo");
//        kevinClientThirdTryMerge.setId(kevinClientDBSaved.getId());
//        em.merge(kevinClientThirdTryMerge);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveParentWithChildren() {
        System.out.println("---------------  START TEST  ---------------");
        Student kevinStudent = new Student("Kevin", "Carrillo");
        Course springCourse = new Course("Spring Boot 6", "Guru");
        Course testCourse = new Course("Test Spring Boot", "Andres");

        kevinStudent.addCourse(springCourse).addCourse(testCourse);
        studentRepository.save(kevinStudent);
        em.persist(kevinStudent);

        Student kevinStudentMerge = em.merge(kevinStudent);
        System.out.println("kevinStudentMerge.getId() = " + kevinStudentMerge.getId());
        kevinStudentMerge.getCourses().forEach(System.out::println);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveParetnWithChildrenAlreadyInDB() {
        System.out.println("---------------  START TEST  ---------------");
        Student kevinStudent = new Student("Kevin", "Carrillo");
        Course springCourse = new Course("Spring Boot 6", "Guru");
        Course testCourse = new Course("Test Spring Boot", "Andres");

        kevinStudent.addCourse(springCourse).addCourse(testCourse);
        studentRepository.save(kevinStudent);
        System.out.println("Saved a student with two courses");

        Student alanStudent = new Student("Alan", "Zaragoza");
        alanStudent.addCourse(springCourse).addCourse(testCourse);

        studentRepository.save(alanStudent);
        em.persist(alanStudent);
        em.merge(alanStudent);

        System.out.println("studentRepository.count() = " + studentRepository.count());
        System.out.println("coursesRepository.count() = " + coursesRepository.count());
        coursesRepository.findAll().forEach(System.out::println);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveChildrenWithoutParent() {
        System.out.println("---------------  START TEST  ---------------");
        ClientDetails clientDetails = new ClientDetails(true, 5000);
        ClientDetails clientDetailsSave = clientDetailsRepository.save(clientDetails);
        System.out.println("clientDetailsSave.getId() = " + clientDetailsSave.getId());
        System.out.println("clientDetailsSave.getClient() = " + clientDetailsSave.getClient());

        em.persist(clientDetails);
        em.merge(clientDetails);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveChildWithParentInitializedButNotInDb() {
        System.out.println("---------------  START TEST  ---------------");
        ClientDetails clientDetails = new ClientDetails(true, 5000);
        Client kevinClient = new Client("Kevin", "Carrillo");
        clientDetails.setClient(kevinClient);

        ClientDetails clientDetailsSave = clientDetailsRepository.save(clientDetails);
        System.out.println("clientDetailsSave.getId() = " + clientDetailsSave.getId());
        System.out.println("clientDetailsSave.getClient() = " + clientDetailsSave.getClient());

        em.persist(clientDetails);
        em.merge(clientDetails);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void saveChildWithParentDetached() {
        System.out.println("---------------  START TEST  ---------------");
        Client kevinClient = new Client("Kevin", "Carrillo");
        em.persist(kevinClient);
        em.flush();
        em.detach(kevinClient);

        ClientDetails clientDetails = new ClientDetails(true, 5000);
        clientDetails.setClient(kevinClient);

        ClientDetails clientDetailsSave = clientDetailsRepository.save(clientDetails);
        System.out.println("clientDetailsSave.getId() = " + clientDetailsSave.getId());
        System.out.println("clientDetailsSave.getClient() = " + clientDetailsSave.getClient());

        em.persist(clientDetails);
        em.merge(clientDetails);

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void updatingParentWithoutCallingSave() {
        System.out.println("---------------  START TEST  ---------------");

        Student kevinStudent = new Student("Kevin", "Carrillo");
        em.persist(kevinStudent);
        em.flush();

        Student kevinStudentSave = studentRepository.findAll().iterator().next();
        System.out.println("kevinStudentSave.getName() = " + kevinStudentSave.getName());
        kevinStudentSave.setName("New Student");

        studentRepository.flush();

        Student kevinStudentUpdate = studentRepository.findAll().iterator().next();
        System.out.println("kevinStudentUpdate.getName() = " + kevinStudentUpdate.getName());

        System.out.println("---------------  END TEST  ---------------");
    }

    @Test
    void updatingInTransaction() {
        System.out.println("---------------  START TEST  ---------------");

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            Student kevinStudent = new Student("Kevin", "Carrillo");
            em.persist(kevinStudent);
            em.flush();

            Student kevinStudentSave = em.createQuery("SELECT s FROM Student s", Student.class).getResultList().getFirst();
            System.out.println("kevinStudentSave.getName() = " + kevinStudentSave.getName());
            kevinStudentSave.setName("New Student");
            em.flush();

            Student kevinStudentSaveFlushing = em.createQuery("SELECT s FROM Student s", Student.class).getResultList().getFirst();
            System.out.println("kevinStudentSaveFlushing.getName() = " + kevinStudentSaveFlushing.getName());
            em.getTransaction().commit();
            em.getTransaction().rollback();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        System.out.println("---------------  END TEST  ---------------");
    }
}