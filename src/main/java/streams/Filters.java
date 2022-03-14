package streams;

import org.apache.log4j.Logger;
import streams.model.Student;

import java.util.List;
import java.util.stream.Collectors;

public class Filters {
    static Logger log = Logger.getLogger(Filters.class);

    List<Student> students;

    public Filters(List<Student> students) {
        this.students = students;
    }


    public void filterStudentsWithNameStartsWithA() {
        log.info("Students with name starting with A");
        students.stream()
                .filter(student -> student.getName().startsWith("A"))
                .forEach(s -> log.info(s.getName()));
    }

    public void filterStudentsThatGet5() {
        log.info("Students that get 5");
        students.stream()
                .filter(student -> student.getGrade() == 5)
                .forEach(s -> log.info(s.getName() + " " + s.getSurname() + " " + s.getGrade()));
    }

    public void filterStudentsThatAreFemale() {
        log.info("Students that are female");
        students.stream()
                .filter(student -> student.getName().endsWith("a"))
                .forEach(s -> log.info(s.getName() + " " + s.getSurname()));
    }

    public void filterStudentsWhichNamesAndSurnamesStartWithDifferentLetter() {
        log.info("Students that have first name and last name with different letter");
        students.stream()
                .filter(student -> student.getName().startsWith(student.getSurname().substring(0,1)))
                .forEach(s -> log.info(s.getName() + " " + s.getSurname()));
    }

    public void filterStudentsThatFailTest() {
        log.info("Students that fail test");
        //ToDo
    }

}
