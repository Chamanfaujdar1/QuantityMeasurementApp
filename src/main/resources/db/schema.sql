CREATE TABLE quantity_measurements(

    id INT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(50),
    measurement_type VARCHAR(50),
    value1 DOUBLE,
    value2 DOUBLE,
    result BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);