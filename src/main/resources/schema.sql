<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS employee (
	employee_id INT PRIMARY KEY,
	employee_name VARCHAR(50),
	age INT
);

CREATE TABLE IF NOT EXISTS m_user (
	user_id VARCHAR(50) PRIMARY KEY,
=======
CREATE TABLE IF NOT EXISTS m_user (
	employee_id VARCHAR(50) PRIMARY KEY,
>>>>>>> origin/master
	password VARCHAR(100),
	user_name VARCHAR(50),
	birthday DATE,
	age INT,
	marriage BOOLEAN,
	role VARCHAR(50)
<<<<<<< HEAD
=======
);

CREATE TABLE IF NOT EXISTS attendance_management (
	employee_id VARCHAR(50),
	user_name VARCHAR(50),
	work_day DATE,
	start_work VARCHAR(5),
	end_work VARCHAR(5),
	lateness VARCHAR(5),
	leave_early VARCHAR(5),
	overtime VARCHAR(5),
	working_time VARCHAR(5),
	status VARCHAR(10),
	remarks VARCHAR(100)
>>>>>>> origin/master
);