package io.github.rad03i2.sms;

import java.io.IOException; import java.util.*;

public final class StudentService {
    private final Store store;
    public StudentService(Store store){this.store=store;}
    public List<Student> students() throws IOException{return store.students();}
    public List<Course> courses() throws IOException{return store.courses();}
    public void addStudent(Student s)throws IOException{var xs=store.students();if(findStudent(xs,s.id())!=null)fail("Student ID already exists: "+s.id());if(xs.stream().anyMatch(x->x.email().equalsIgnoreCase(s.email())))fail("Email already exists: "+s.email());xs.add(s);store.saveStudents(xs);}
    public void updateStudent(Student s)throws IOException{var xs=store.students();Student old=findStudent(xs,s.id());if(old==null)fail("Student not found: "+s.id());if(xs.stream().anyMatch(x->!x.id().equalsIgnoreCase(s.id())&&x.email().equalsIgnoreCase(s.email())))fail("Email already exists: "+s.email());xs.set(xs.indexOf(old),s);store.saveStudents(xs);}
    public void removeStudent(String id)throws IOException{var es=store.enrollments();if(es.stream().anyMatch(e->e.studentId().equalsIgnoreCase(id)))fail("Cannot remove enrolled student: "+id);var xs=store.students();Student s=findStudent(xs,id);if(s==null)fail("Student not found: "+id);xs.remove(s);store.saveStudents(xs);}
    public void addCourse(Course c)throws IOException{var xs=store.courses();if(findCourse(xs,c.code())!=null)fail("Course already exists: "+c.code());xs.add(c);store.saveCourses(xs);}
    public void removeCourse(String code)throws IOException{if(store.enrollments().stream().anyMatch(e->e.courseCode().equalsIgnoreCase(code)))fail("Cannot remove course with enrollments: "+code);var xs=store.courses();Course c=findCourse(xs,code);if(c==null)fail("Course not found: "+code);xs.remove(c);store.saveCourses(xs);}
    public void enroll(String sid,String code)throws IOException{if(findStudent(store.students(),sid)==null)fail("Student not found: "+sid);if(findCourse(store.courses(),code)==null)fail("Course not found: "+code);var xs=store.enrollments();if(findEnrollment(xs,sid,code)!=null)fail("Enrollment already exists");xs.add(new Enrollment(sid,code,null));store.saveEnrollments(xs);}
    public void unenroll(String sid,String code)throws IOException{var xs=store.enrollments();Enrollment e=findEnrollment(xs,sid,code);if(e==null)fail("Enrollment not found");xs.remove(e);store.saveEnrollments(xs);}
    public void grade(String sid,String code,double grade)throws IOException{var xs=store.enrollments();Enrollment e=findEnrollment(xs,sid,code);if(e==null)fail("Enrollment not found");xs.set(xs.indexOf(e),new Enrollment(e.studentId(),e.courseCode(),grade));store.saveEnrollments(xs);}
    public List<Enrollment> enrollmentsForStudent(String id)throws IOException{return store.enrollments().stream().filter(e->e.studentId().equalsIgnoreCase(id)).toList();}
    public List<Enrollment> enrollmentsForCourse(String code)throws IOException{return store.enrollments().stream().filter(e->e.courseCode().equalsIgnoreCase(code)).toList();}
    public double gpa(String id)throws IOException{var courses=store.courses();double points=0;int credits=0;for(var e:enrollmentsForStudent(id)){if(e.grade()==null)continue;Course c=findCourse(courses,e.courseCode());if(c!=null){points+=gradePoint(e.grade())*c.credits();credits+=c.credits();}}return credits==0?0:points/credits;}
    static double gradePoint(double g){if(g>=90)return 4.0;if(g>=80)return 3.0;if(g>=70)return 2.0;if(g>=60)return 1.0;return 0.0;}
    public Student student(String id)throws IOException{Student s=findStudent(store.students(),id);if(s==null)fail("Student not found: "+id);return s;}
    public Course course(String code)throws IOException{Course c=findCourse(store.courses(),code);if(c==null)fail("Course not found: "+code);return c;}
    private static Student findStudent(List<Student>x,String id){return x.stream().filter(s->s.id().equalsIgnoreCase(id)).findFirst().orElse(null);} private static Course findCourse(List<Course>x,String c){return x.stream().filter(v->v.code().equalsIgnoreCase(c)).findFirst().orElse(null);} private static Enrollment findEnrollment(List<Enrollment>x,String s,String c){return x.stream().filter(e->e.studentId().equalsIgnoreCase(s)&&e.courseCode().equalsIgnoreCase(c)).findFirst().orElse(null);} private static void fail(String m){throw new IllegalArgumentException(m);}
}
