INSERT INTO m_user (employee_id, password, user_name, birthday, age, marriage, role) VALUES 
('1234567', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'asdf', '1990-01-01', 30, false, 'ROLE_ADMIN'),
('9876543', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'zxcv', '1986-01-01', 34, false, 'ROLE_GENERAL');

INSERT INTO attendance_management (employee_id, user_name, work_day, start_work, end_work, lateness, leave_early, overtime, working_time, status, remarks) VALUES 
('1234567', 'asdf', '2020-04-11', '09:00', '18:00', '00:00', '00:00', '00:00', '08:00', '退社', ''),
('1234567', 'asdf', '2020-04-02', '09:00', '18:00', '00:00', '00:00', '00:00', '08:00', '退社', ''),
('9876543', 'zxcv', '2020-04-01', '09:00', '18:00', '00:00', '00:00', '00:00', '08:00', '退社', '');
