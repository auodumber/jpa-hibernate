-- Create Student table
CREATE TABLE Student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Course table
CREATE TABLE Course (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

-- Create Enrollment table
CREATE TABLE Enrollment (
    id SERIAL PRIMARY KEY,
    enrollment_date DATE NOT NULL,
    student_id INT REFERENCES Student(id) ON DELETE CASCADE,
    course_id INT REFERENCES Course(id) ON DELETE CASCADE
);

-- Insert students
INSERT INTO Student (id, name) VALUES (1, 'Alice');
INSERT INTO Student (id, name) VALUES (2, 'Bob');
INSERT INTO Student (id, name) VALUES (3, 'Charlie');

-- Insert courses
INSERT INTO Course (id, title) VALUES (1, 'Mathematics');
INSERT INTO Course (id, title) VALUES (2, 'Physics');
INSERT INTO Course (id, title) VALUES (3, 'Chemistry');

-- Insert enrollments
INSERT INTO Enrollment (id, enrollment_date, student_id, course_id) VALUES (1, '2023-10-10', 1, 1);
INSERT INTO Enrollment (id, enrollment_date, student_id, course_id) VALUES (2, '2023-10-09', 1, 2);
INSERT INTO Enrollment (id, enrollment_date, student_id, course_id) VALUES (3, '2023-09-15', 2, 2);
INSERT INTO Enrollment (id, enrollment_date, student_id, course_id) VALUES (4, '2023-08-20', 3, 3);
