select 'drop table ' || table_name || ';' from user_tables;
select 'drop sequence ' || sequence_name || ';' from user_sequences;
select 'drop trigger ' || trigger_name || ';' stmt from user_triggers;

/*
*Creation of the table module
*/
CREATE TABLE MODULE (
  id NUMBER(5),
  name VARCHAR2(50) NOT NULL UNIQUE,
  CONSTRAINT PK_MODULE_ID PRIMARY KEY (id)
);

/*
*Creation of the sequence module
*/
CREATE SEQUENCE MODULE_SEQ
START WITH 10000
INCREMENT BY 1;

/*
*Creation of the trigger module
*/
CREATE OR REPLACE TRIGGER MODULE_TRIGGER
  BEFORE INSERT ON MODULE
  FOR EACH ROW
BEGIN
  SELECT MODULE_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the table quotaAbsence
*/
CREATE TABLE QUOTA_ABSENCE (
  id NUMBER(5),
  hours VARCHAR2(3) NOT NULL UNIQUE,
  CONSTRAINT PK_QUOTA_ABSENCE_ID PRIMARY KEY (id)
);

/*
*Creation of the sequence quota
*/
CREATE SEQUENCE QUOTA_ABSENCE_SEQ
START WITH 20000
INCREMENT BY 1;

/*
*Creation of the trigger quota
*/
CREATE OR REPLACE TRIGGER QUOTA_ABSENCE_TRIGGER
  BEFORE INSERT ON QUOTA_ABSENCE
  FOR EACH ROW
BEGIN
  SELECT QUOTA_ABSENCE_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the table typeAbsence
*/
CREATE TABLE TYPE_ABSENCE (
  id NUMBER(5),
  entitle VARCHAR2(50) NOT NULL UNIQUE,
  CONSTRAINT PK_TYPE_ABSENCE_ID PRIMARY KEY (id)
);

/*
*Creation of the sequence type absence
*/
CREATE SEQUENCE TYPE_ABSENCE_SEQ
START WITH 30000
INCREMENT BY 1;

/*
*Creation of the trigger type absence
*/
CREATE OR REPLACE TRIGGER TYPE_ABSENCE_TRIGGER
  BEFORE INSERT ON TYPE_ABSENCE
  FOR EACH ROW
BEGIN
  SELECT TYPE_ABSENCE_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the table course
*/
CREATE TABLE COURSE (
  id      NUMBER(5),
  name VARCHAR2(50) NOT NULL UNIQUE,
  hourlyMass NUMBER(3)      NOT NULL,
  module     NUMBER(5)      NOT NULL,
  CONSTRAINT PK_COURSE_ID PRIMARY KEY (id),
  CONSTRAINT FK_COURSE_MODULE FOREIGN KEY (module)
  REFERENCES MODULE (id)
);

/*
*Creation of the sequence course
*/
CREATE SEQUENCE COURSE_SEQ
START WITH 40000
INCREMENT BY 1;

/*
*Creation of the trigger course
*/
CREATE OR REPLACE TRIGGER COURSE_TRIGGER
  BEFORE INSERT ON COURSE
  FOR EACH ROW
BEGIN
  SELECT COURSE_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the table teacher
*/
CREATE TABLE TEACHER (
  id       NUMBER(5),
  firstName         VARCHAR(50) NOT NULL,
  lastName          VARCHAR(50) NOT NULL,
  phoneNumber       VARCHAR(50) UNIQUE,
  password          VARCHAR(50) NOT NULL UNIQUE,
  CONSTRAINT PK_TEACHER_ID PRIMARY KEY (id),
  CONSTRAINT UK_TEACHER UNIQUE(firstName, lastName)
);

/*
*Creation of the sequence teacher
*/
CREATE SEQUENCE TEACHER_SEQ
START WITH 50000
INCREMENT BY 1;

/*
*Creation of the trigger teacher
*/
CREATE OR REPLACE TRIGGER TEACHER_TRIGGER
  BEFORE INSERT ON TEACHER
  FOR EACH ROW
BEGIN
  SELECT TEACHER_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the correspondence table teacher_course
*/
CREATE TABLE TEACHER_COURSE(
  id_teacher     NUMBER(5),
  id_course      NUMBER(5),
  CONSTRAINT FK_TEACHER_COURSE_ID_TEACHER FOREIGN KEY (id_teacher)
  REFERENCES TEACHER (id) ON DELETE CASCADE,
  CONSTRAINT FK_TEACHER_COURSE_ID_COURSE FOREIGN KEY (id_course)
  REFERENCES COURSE (id)
);

/*
*Creation of the  table group
*/
CREATE TABLE GROUPE(
  id NUMBER(2),
  maxCapacity NUMBER(2),
  CONSTRAINT PK_GROUP_ID PRIMARY KEY (id)
);

CREATE SEQUENCE GROUP_SEQ
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER GROUP_TRIGGER
  BEFORE INSERT ON GROUPE
  FOR EACH ROW
BEGIN
  SELECT GROUP_SEQ.nextval
    INTO :new.id
    FROM DUAL;
END;
/

/*
*Creation of the table student
*/
CREATE TABLE STUDENT (
  id NUMBER(5),
  firstName VARCHAR2(50) NOT NULL,
  lastName VARCHAR2(50) NOT NULL,
  email VARCHAR2(50) NOT NULL UNIQUE,
  password VARCHAR2(50) NOT NULL UNIQUE,
  faculty VARCHAR2(15),
  id_group NUMBER(2),
  CONSTRAINT PK_STUDENT_ID PRIMARY KEY (id),
  CONSTRAINT UK_STUDENT UNIQUE (firstName, lastName),
  CONSTRAINT FK_STUDENT_ID_GROUP FOREIGN KEY (id_group) REFERENCES GROUPE(id) ON DELETE SET NULL
);

/*
*Creation of the sequence student
*/
CREATE SEQUENCE STUDENT_SEQ
START WITH 60000
INCREMENT BY 1;

/*
*Creation of the trigger student
*/
CREATE OR REPLACE TRIGGER STUDENT_TRIGGER
  BEFORE INSERT ON STUDENT
  FOR EACH ROW
BEGIN
  SELECT STUDENT_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the correspondence table group_teacher_course
*/
CREATE TABLE TC_GROUP(
  id	NUMBER(5),
  id_group      NUMBER(2),
  id_course     NUMBER(5),
  id_teacher     NUMBER(5),
  CONSTRAINT PK_TC_GROUP PRIMARY KEY (id),
  CONSTRAINT FK_TC_GROUP_ID_GROUP FOREIGN KEY (id_group)
  REFERENCES GROUPE (id) ON DELETE SET NULL,
  CONSTRAINT FK_TC_GROUP_ID_COURSE FOREIGN KEY (id_course)
  REFERENCES COURSE(id),
  CONSTRAINT FK_TC_GROUP_ID_TEACHER FOREIGN KEY (id_teacher)
  REFERENCES TEACHER(id) ON DELETE SET NULL
  
);

/*
*Creation of the sequence teacher_course_group
*/
CREATE SEQUENCE TC_GROUP_SEQ
START WITH 70000
INCREMENT BY 1;

/*
*Creation of the trigger teacher_course_group
*/
CREATE OR REPLACE TRIGGER TC_GROUP_TRIGGER
  BEFORE INSERT ON TC_GROUP
  FOR EACH ROW
BEGIN
  SELECT TC_GROUP_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the table lesson
*/
CREATE TABLE LESSON(
  id	NUMBER(5),
  id_tc_group NUMBER(5),
  typeLesson VARCHAR2(50),
  theDay NUMBER(1),
  startTime NUMBER(4,2),
  endTime NUMBER(4,2),
  rate NUMBER(4,2),
  CONSTRAINT PK_LESSON_ID PRIMARY KEY (id),
  CONSTRAINT FK_LESSON_ID_TC_GROUP FOREIGN KEY (id_tc_group)
  REFERENCES TC_GROUP (id)
);

/*
*Creation of the sequence teacher_course_group
*/
CREATE SEQUENCE LESSON_SEQ
START WITH 80000
INCREMENT BY 1;

/*
*Creation of the trigger teacher_course_group
*/
CREATE OR REPLACE TRIGGER LESSON_TRIGGER
  BEFORE INSERT ON LESSON
  FOR EACH ROW
BEGIN
  SELECT LESSON_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the correspondance table lesson_week
*/
CREATE TABLE LESSON_WEEK(
  id_lesson	NUMBER(5),
  week NUMBER(2),
  absence_rate NUMBER(4,2),
  absence_conserved NUMBER(1),
  CONSTRAINT PK_LESSON_WEEK PRIMARY KEY (id_lesson, week),
  CONSTRAINT FK_LESSON_WEEK_ID_LESSON FOREIGN KEY (id_lesson) REFERENCES LESSON (id) ON DELETE CASCADE
);

CREATE TABLE PROOF(
  id NUMBER(5),
  id_student NUMBER(5),
  proof_path VARCHAR(200) UNIQUE NOT NULL,
  CONSTRAINT PK_PROOF_ID PRIMARY KEY(id),
  CONSTRAINT FK_PROOF_ID_STUDENT FOREIGN KEY(id_student) REFERENCES student(id)
);

CREATE SEQUENCE PROOF_SEQ
START WITH 90000
INCREMENT BY 1;

CREATE TRIGGER PROOF_TRIGGER
  BEFORE INSERT ON PROOF
  FOR EACH ROW
BEGIN
  SELECT PROOF_SEQ.nextval
    INTO :new.id
    FROM dual;
END;
/

/*
*Creation of the table absence
*/
CREATE TABLE ABSENCE(
  id_lesson NUMBER(5),
  id_week NUMBER(2),
  id_student NUMBER(5),
  id_type_absence NUMBER(5),
  id_proof NUMBER(5),
  isQuota NUMBER(1),
  isJustified NUMBER(1),
  CONSTRAINT PK_ABSENCE PRIMARY KEY (id_lesson, id_week, id_student),
  CONSTRAINT FK_ABSENCE_ID_LESSON FOREIGN KEY (id_lesson, id_week) REFERENCES LESSON_WEEK(id_lesson,week),
  CONSTRAINT FK_ABSENCE_ID_STUDENT FOREIGN KEY (id_student) REFERENCES STUDENT(id),
  CONSTRAINT FK_ABSENCE_ID_TYPE_ABSENCE FOREIGN KEY (id_type_absence) REFERENCES TYPE_ABSENCE(id),
  CONSTRAINT FK_ABSENCE_ID_PROOF FOREIGN KEY(id_proof) REFERENCES PROOF(ID) ON DELETE SET NULL
);

