# Student Management System

A practical, dependency-free Java 17 CLI for managing students, courses, enrollments, grades, and persistent local records.

## English

### Overview
Student Management System is a small but complete command-line application for schools, training centers, and Java learners. It stores data locally, validates input, prevents duplicate IDs/enrollments, and provides student transcripts and course rosters.

### Why it exists
The original repository was an educational prototype. This version turns it into a runnable end-to-end application while keeping the stack intentionally simple: Java 17 and the standard library only.

### Features
- Add, list, find, update, and remove students.
- Add and list courses with credit hours.
- Enroll students in courses and record grades from 0–100.
- Student transcript with weighted GPA on a 4.0 scale.
- Course roster view.
- Durable TSV-based local storage with atomic file replacement.
- Case-insensitive email uniqueness and basic email validation.
- Referential-integrity checks: a student/course cannot be removed while enrolled.
- Human-readable CLI errors and non-zero exit codes.
- UTF-8 data, including Arabic names.
- No network calls, telemetry, accounts, or secrets.

### Preview
```text
$ java -jar target/student-management-system.jar student add S001 "Sara Ali" sara@example.com
Student S001 added.
$ java -jar target/student-management-system.jar course add CS101 "Programming I" 3
Course CS101 added.
$ java -jar target/student-management-system.jar enroll S001 CS101
Enrollment created.
$ java -jar target/student-management-system.jar grade S001 CS101 91
Grade updated.
$ java -jar target/student-management-system.jar transcript S001
S001 | Sara Ali | sara@example.com
CS101 | Programming I | credits=3 | grade=91.0
GPA: 4.00
```

### Requirements & installation
Requires JDK 17+ and Maven 3.9+.
```bash
git clone https://github.com/rad03i2/student-management-system.git
cd student-management-system
mvn clean package
java -jar target/student-management-system.jar help
```
For a temporary database location:
```bash
java -Dsms.dataDir=./demo-data -jar target/student-management-system.jar student list
```
By default data is stored in `./data`.

### Usage
```bash
java -jar target/student-management-system.jar student add S001 "Sara Ali" sara@example.com
java -jar target/student-management-system.jar student list
java -jar target/student-management-system.jar student update S001 "Sara Ahmed" sara@example.com
java -jar target/student-management-system.jar course add CS101 "Programming I" 3
java -jar target/student-management-system.jar course list
java -jar target/student-management-system.jar enroll S001 CS101
java -jar target/student-management-system.jar grade S001 CS101 85
java -jar target/student-management-system.jar transcript S001
java -jar target/student-management-system.jar roster CS101
java -jar target/student-management-system.jar unenroll S001 CS101
java -jar target/student-management-system.jar student remove S001
```
Run `help` for the complete command reference.

### Configuration
Only one optional JVM property is used: `sms.dataDir`, the directory containing `students.tsv`, `courses.tsv`, and `enrollments.tsv`. No `.env` file is required.

### Project structure
```text
src/main/java/io/github/rad03i2/sms/  domain, repository, service, CLI
src/test/java/io/github/rad03i2/sms/  dependency-free integration tests
.github/workflows/ci.yml               build/test CI
data/                                  runtime data (gitignored)
```

### Testing
```bash
mvn test
```
The test harness exercises CRUD, duplicate validation, enrollment integrity, grading, persistence, GPA, and Arabic UTF-8 names. CI builds and tests on Linux, Windows, and macOS with Java 17 and 21.

### Security & privacy
All records remain local. Fields are escaped before TSV persistence, atomic replacement reduces partial-write risk, and the application never executes stored values. The data directory may contain personal information; protect and back it up appropriately.

### Limitations
This is a single-user local CLI, not a multi-user SIS. It has no authentication, web UI, attendance, scheduling, database server, concurrent-writer locking, or regulatory compliance claims. The simple GPA mapping is documented in source and may differ from an institution's policy.

### Optional roadmap
A future release may add CSV import/export, attendance, and a REST/UI layer without changing the core domain model.

### Contributing
See [CONTRIBUTING.md](CONTRIBUTING.md). Please keep changes focused, tested, and dependency-light.

### License
MIT — see [LICENSE](LICENSE).

### Author
**Radwan Abdulhadi Ahmed**  
**رضوان عبدالهادي أحمد**  
GitHub: [@rad03i2](https://github.com/rad03i2)

---

## العربية

### نظرة عامة
نظام إدارة الطلاب هو تطبيق سطر أوامر عملي مكتوب بـ Java 17 لإدارة الطلاب والمقررات والتسجيل والدرجات والسجل الدراسي. يعمل محليًا بالكامل ويحفظ البيانات بصورة دائمة دون خدمات خارجية.

### لماذا المشروع؟
بدأ المستودع كنموذج تعليمي صغير، وأصبح الآن تطبيقًا متكاملًا قابلًا للتشغيل من البداية إلى النهاية مع الحفاظ على بساطة التقنية والاعتماد على مكتبة Java القياسية فقط.

### الميزات
- إضافة الطلاب وعرضهم والبحث عنهم وتعديلهم وحذفهم.
- إنشاء المقررات مع عدد الساعات المعتمدة.
- تسجيل الطالب في مقرر وإلغاء التسجيل.
- إدخال درجات من 0 إلى 100.
- إنشاء سجل دراسي وحساب GPA موزون بالساعات على مقياس 4.0.
- عرض قائمة طلاب كل مقرر.
- تخزين محلي دائم بصيغة TSV مع استبدال ذري للملفات.
- منع تكرار المعرفات والبريد الإلكتروني والتسجيل المكرر.
- حماية التكامل المرجعي عند محاولة حذف طالب أو مقرر مرتبط بتسجيلات.
- دعم UTF-8 والأسماء العربية.
- لا اتصال بالشبكة ولا تتبع ولا مفاتيح سرية.

### المعاينة والتثبيت
المتطلبات: JDK 17+ وMaven 3.9+.
```bash
git clone https://github.com/rad03i2/student-management-system.git
cd student-management-system
mvn clean package
java -jar target/student-management-system.jar help
```
المجلد الافتراضي للبيانات هو `./data`. ويمكن تغييره:
```bash
java -Dsms.dataDir=./my-data -jar target/student-management-system.jar student list
```

### الاستخدام
```bash
java -jar target/student-management-system.jar student add S001 "سارة علي" sara@example.com
java -jar target/student-management-system.jar course add ENV101 "هندسة البيئة" 3
java -jar target/student-management-system.jar enroll S001 ENV101
java -jar target/student-management-system.jar grade S001 ENV101 90
java -jar target/student-management-system.jar transcript S001
java -jar target/student-management-system.jar roster ENV101
```
استخدم الأمر `help` لرؤية جميع الأوامر.

### الإعداد وبنية المشروع
لا يحتاج المشروع إلى `.env`. الخاصية الاختيارية الوحيدة هي `sms.dataDir`. توجد الشفرة الأساسية تحت `src/main/java` والاختبارات تحت `src/test/java`، بينما تُحفظ بيانات التشغيل في `data/` وهي مستثناة من Git.

### الاختبارات
```bash
mvn test
```
تغطي الاختبارات CRUD والتحقق من التكرار والتسجيل والدرجات والحفظ وإعادة القراءة وحساب GPA ودعم الأسماء العربية. ويقوم CI بالبناء والاختبار على Linux وWindows وmacOS.

### الأمان والخصوصية
تبقى البيانات على الجهاز ولا تُرسل إلى أي خدمة. قد تحتوي ملفات البيانات على معلومات شخصية، لذلك يجب حمايتها ونسخها احتياطيًا وفق احتياجات المستخدم أو المؤسسة.

### القيود
المشروع تطبيق محلي لمستخدم واحد، وليس نظام معلومات طلاب مؤسسي متعدد المستخدمين. لا يوفر حاليًا تسجيل دخول أو واجهة ويب أو حضورًا أو جدولة أو قفلًا للكتابة المتزامنة، ولا يدّعي توافقًا تنظيميًا. وقد تختلف سياسة GPA بين المؤسسات.

### التطوير المستقبلي الاختياري
يمكن مستقبلًا إضافة استيراد/تصدير CSV والحضور وواجهة REST أو ويب مع الإبقاء على نموذج المجال الحالي.

### المساهمة والترخيص
راجع [CONTRIBUTING.md](CONTRIBUTING.md). المشروع مرخص برخصة MIT؛ راجع [LICENSE](LICENSE).

### المؤلف
**Radwan Abdulhadi Ahmed**  
**رضوان عبدالهادي أحمد**  
GitHub: [@rad03i2](https://github.com/rad03i2)
