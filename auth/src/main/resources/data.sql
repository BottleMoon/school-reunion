-- Member 데이터 삽입
INSERT INTO member (email, name, is_deleted)
VALUES ('member1@example.com', 'Member 1', false);
INSERT INTO member (email, name, is_deleted)
VALUES ('member2@example.com', 'Member 2', false);


-- School 데이터 삽입
insert into school(name)
values ('test');


-- Reunion 데이터 삽입
INSERT into reunion(school_id, grade, year)
values (1, 1, 20);
INSERT into reunion(school_id, grade, year)
values (1, 2, 21);


-- ReunionMember 데이터 삽입
INSERT INTO reunion_member(member_id, reunion_id)
values (1, 1);
INSERT INTO reunion_member(member_id, reunion_id)
values (1, 2);
INSERT INTO reunion_member(member_id, reunion_id)
values (2, 1);
INSERT INTO reunion_member(member_id, reunion_id)
values (2, 2);


-- Role 데이터 삽입
INSERT INTO role(id, role_name)
VALUES (1, 'ROLE_USER');