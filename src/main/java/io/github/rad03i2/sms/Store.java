package io.github.rad03i2.sms;

import java.io.*; import java.nio.charset.StandardCharsets; import java.nio.file.*; import java.util.*; import java.util.function.Function;

public final class Store {
    private final Path dir;
    public Store(Path dir) { this.dir = Objects.requireNonNull(dir); }
    public List<Student> students() throws IOException { return read("students.tsv", p -> new Student(p[0], p[1], p[2]), 3); }
    public List<Course> courses() throws IOException { return read("courses.tsv", p -> new Course(p[0], p[1], Integer.parseInt(p[2])), 3); }
    public List<Enrollment> enrollments() throws IOException { return read("enrollments.tsv", p -> new Enrollment(p[0], p[1], p[2].isEmpty()?null:Double.valueOf(p[2])), 3); }
    public void saveStudents(Collection<Student> xs) throws IOException { write("students.tsv", xs.stream().map(s -> row(s.id(),s.name(),s.email())).toList()); }
    public void saveCourses(Collection<Course> xs) throws IOException { write("courses.tsv", xs.stream().map(c -> row(c.code(),c.title(),String.valueOf(c.credits()))).toList()); }
    public void saveEnrollments(Collection<Enrollment> xs) throws IOException { write("enrollments.tsv", xs.stream().map(e -> row(e.studentId(),e.courseCode(),e.grade()==null?"":String.valueOf(e.grade()))).toList()); }
    private <T> List<T> read(String file, Function<String[],T> mapper, int fields) throws IOException {
        Path p=dir.resolve(file); if(!Files.exists(p)) return new ArrayList<>(); List<T> out=new ArrayList<>(); int lineNo=0;
        for(String line:Files.readAllLines(p,StandardCharsets.UTF_8)){ lineNo++; if(line.isBlank()) continue; String[] a=line.split("\\t",-1); if(a.length!=fields) throw new IOException("Malformed "+file+" line "+lineNo); try{ for(int i=0;i<a.length;i++) a[i]=unescape(a[i]); out.add(mapper.apply(a)); }catch(RuntimeException ex){ throw new IOException("Invalid "+file+" line "+lineNo+": "+ex.getMessage(),ex); } } return out;
    }
    private void write(String file,List<String> lines)throws IOException{ Files.createDirectories(dir); Path target=dir.resolve(file), tmp=Files.createTempFile(dir,file,".tmp"); try{ Files.write(tmp,lines,StandardCharsets.UTF_8,StandardOpenOption.TRUNCATE_EXISTING); try{Files.move(tmp,target,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(AtomicMoveNotSupportedException e){Files.move(tmp,target,StandardCopyOption.REPLACE_EXISTING);} }finally{Files.deleteIfExists(tmp);} }
    private static String row(String...v){return String.join("\t",Arrays.stream(v).map(Store::escape).toList());}
    private static String escape(String s){return s.replace("\\","\\\\").replace("\t","\\t").replace("\n","\\n").replace("\r","\\r");}
    private static String unescape(String s){StringBuilder b=new StringBuilder();boolean esc=false;for(char c:s.toCharArray()){if(esc){b.append(switch(c){case't'->'\t';case'n'->'\n';case'r'->'\r';case'\\'->'\\';default->c;});esc=false;}else if(c=='\\')esc=true;else b.append(c);}if(esc)b.append('\\');return b.toString();}
}
