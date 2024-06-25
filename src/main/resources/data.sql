

insert into USERS(username, password) values('user', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');
insert into USERS(username, password) values('admin', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');
insert into USERS(username, password) values('read_only', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');
insert into USERS(username, password) values('dummy', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');

insert into roles(name) values('USER');
insert into roles(name) values('ADMIN');
insert into roles(name) values('READ_ONLY');
insert into roles(name) values('DUMMY');

insert into role_user(application_user_id, role_id) values(1, 1);
insert into role_user(application_user_id, role_id) values(2, 1);
insert into role_user(application_user_id, role_id) values(2, 2);
insert into role_user(application_user_id, role_id) values(3, 3);

insert into category (category_id, name) values (1, 'Earbuds Headphones');
insert into category (category_id, name) values (2, 'On-ear Headphone');
insert into category (category_id, name) values (3, 'Gaming Headphones');


insert into product (id, description, image_name, name, price, category_id) values (1, 'air-pods 2', 'pods2.jpg', 'Air Pods 2', 170.0, 1);
insert into product (id, description, image_name, name, price, category_id) values (2, 'air-pods 3', 'pods3.jpg', 'Air Pods 3', 190.0, 1);
insert into product (id, description, image_name, name, price, category_id) values (3, 'air-pods pro', 'pro.png', 'Air Pods Pro', 200.0, 1);
insert into product (id, description, image_name, name, price, category_id) values (20, 'air-pods max', 'max.jpeg', 'Air Pods Max', 350.0, 2);
insert into product (id, description, image_name, name, price, category_id) values (30, 'Soundcore q45', 'q45.jpg', 'Soundcore Q45', 150.0, 2);
insert into product (id, description, image_name, name, price, category_id) values (40, 'Soundcore q35', 'q35.jpg', 'Soundcore Q35', 120.0, 2);
insert into product (id, description, image_name, name, price, category_id) values (50, 'Sony wh-1000xm5', 'wh-1000xm5.jpg', 'Sony Wh-1000xm5', 200.0, 2);
insert into product (id, description, image_name, name, price, category_id) values (100, 'HyperX Cloud', 'cloud.jpg', 'HyperX Cloud', 180.0, 3);

