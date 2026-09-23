package io.github.rad03i2.sms;

import java.nio.file.*; import java.util.*;

public final class Main {
    private Main(){}
    public static void main(String[] args){int code=run(args);if(code!=0)System.exit(code);}
    static int run(String[] a){try{if(a.length==0||a[0].equals("help")||a[0].equals("--help")){help();return 0;}if(a[0].equals("--version")){System.out.println("student-management-system 1.0.0 — Radwan Abdulhadi Ahmed / @rad03i2");return 0;}var s=new StudentService(new Store(Path.of(System.getProperty("sms.dataDir","data"))));switch(a[0]){
        case "student" -> student(s,a); case "course" -> course(s,a); case "enroll" -> {need(a,3);s.enroll(a[1],a[2]);System.out.println("Enrollment created.");} case "unenroll" -> {need(a,3);s.unenroll(a[1],a[2]);System.out.println("Enrollment removed.");} case "grade" -> {need(a,4);s.grade(a[1],a[2],Double.parseDouble(a[3]));System.out.println("Grade updated.");} case "transcript" -> transcript(s,a); case "roster" -> roster(s,a); default -> throw new IllegalArgumentException("Unknown command: "+a[0]);}return 0;}catch(NumberFormatException e){System.err.println("Error: expected a number");return 2;}catch(Exception e){System.err.println("Error: "+e.getMessage());return 2;}}
    private static void student(StudentService s,String[]a)throws Exception{need(a,2);switch(a[1]){case"add"->{need(a,5);s.addStudent(new Student(a[2],a[3],a[4]));System.out.println("Student "+a[2]+" added.");}case"update"->{need(a,5);s.updateStudent(new Student(a[2],a[3],a[4]));System.out.println("Student updated.");}case"remove"->{need(a,3);s.removeStudent(a[2]);System.out.println("Student removed.");}case"list"->s.students().stream().sorted(Comparator.comparing(Student::id)).forEach(x->System.out.printf("%s | %s | %s%n",x.id(),x.name(),x.email()));case"show"->{need(a,3);var x=s.student(a[2]);System.out.printf("%s | %s | %s%n",x.id(),x.name(),x.email());}default->throw new IllegalArgumentException("Unknown student action: "+a[1]);}}
    private static void course(StudentService s,String[]a)throws Exception{need(a,2);switch(a[1]){case"add"->{need(a,5);s.addCourse(new Course(a[2],a[3],Integer.parseInt(a[4])));System.out.println("Course "+a[2]+" added.");}case"remove"->{need(a,3);s.removeCourse(a[2]);System.out.println("Course removed.");}case"list"->s.courses().stream().sorted(Comparator.comparing(Course::code)).forEach(x->System.out.printf("%s | %s | credits=%d%n",x.code(),x.title(),x.credits()));default->throw new IllegalArgumentException("Unknown course action: "+a[1]);}}
    private static void transcript(StudentService s,String[]a)throws Exception{need(a,2);var st=s.student(a[1]);System.out.printf("%s | %s | %s%n",st.id(),st.name(),st.email());for(var e:s.enrollmentsForStudent(st.id())){var c=s.course(e.courseCode());System.out.printf("%s | %s | credits=%d | grade=%s%n",c.code(),c.title(),c.credits(),e.grade()==null?"pending":e.grade());}System.out.printf(Locale.ROOT,"GPA: %.2f%n",s.gpa(st.id()));}
    private static void roster(StudentService s,String[]a)throws Exception{need(a,2);var c=s.course(a[1]);System.out.println(c.code()+" | "+c.title());for(var e:s.enrollmentsForCourse(c.code())){var st=s.student(e.studentId());System.out.printf("%s | %s | grade=%s%n",st.id(),st.name(),e.grade()==null?"pending":e.grade());}}
    private static void need(String[]a,int n){if(a.length!=n)throw new IllegalArgumentException("Invalid argument count; run help");}
    private static void help(){System.out.println("""
Student Management System 1.0.0
student add <id> <name> <email> | student update <id> <name> <email>
student list | student show <id> | student remove <id>
course add <code> <title> <credits> | course list | course remove <code>
enroll <studentId> <courseCode> | unenroll <studentId> <courseCode>
grade <studentId> <courseCode> <0-100> | transcript <studentId> | roster <courseCode>
Data directory: -Dsms.dataDir=<path>
Author: Radwan Abdulhadi Ahmed / رضوان عبدالهادي أحمد / @rad03i2
""");}
}
