CREATE TABLE USERS (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE role_user (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           application_user_id INT,
                           role_id INT,
                           FOREIGN KEY (application_user_id) REFERENCES USERS(id),
                           FOREIGN KEY (role_id) REFERENCES roles(id)
);
CREATE SEQUENCE category_seq START WITH 5 INCREMENT BY 1;

CREATE TABLE category (
                          category_id INT DEFAULT NEXT VALUE FOR category_seq PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE SEQUENCE PRODUCT_SEQ START WITH 15 INCREMENT BY 1;

CREATE TABLE product (
                         id BIGINT DEFAULT NEXT VALUE FOR PRODUCT_SEQ PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(10, 2),
                         description VARCHAR(255),
                         image_name VARCHAR(255),
                         category_id INT,
                         FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE cart_item (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           product_id INT,
                           quantity INT NOT NULL,
                           user_id BIGINT,
                           date DATE,
                           payment_method VARCHAR(255),
                           FOREIGN KEY (product_id) REFERENCES product(id),
                           FOREIGN KEY (user_id) REFERENCES USERS(id)
);

CREATE TABLE Logs (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255),
                      ip_address VARCHAR(255),
                      date TIMESTAMP
);