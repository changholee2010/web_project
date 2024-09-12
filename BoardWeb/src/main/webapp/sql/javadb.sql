-- fullcalendar ������.
create table tbl_calendar (
  title varchar2(1000) not null
 ,start_date varchar2(20) not null
 ,end_date varchar2(20)
);

insert into tbl_calendar values('ȸ��1', '202409-12T16:00:00', '202409-12T18:00:00');
insert into tbl_calendar values('�߼�����', '2024-09-16', '2024-09-19');

select *
from tbl_calendar;

delete from tbl_calendar
where title like 'ȸ��1';

create table tbl_todo (
  todo_id number primary key,
  todo_date varchar2(20) not null, -- '2024-09-08'
  todo_title varchar2(300) not null,
  creation_date date default sysdate
);
create sequence todo_seq;

insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-13', '���� ��Ʈ');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-13', '��ٱ��� ����');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-13', '�ڹ� Ŭ���� �������̽�');

insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-12', '�ڹ� Ŭ����2');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-12', '�ڹ� Ŭ���� ���2');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-12', '�ڹ� Ŭ���� �������̽�2');


select *
from tbl_todo
where todo_date = to_char(sysdate, 'yyyy-mm-dd');

delete from tbl_todo;




select b.*
from (select /*+ INDEX_DESC (r PK_REPLY) */ rownum rn, r.*
      from tbl_reply r
      where board_no = 147 ) b
where b.rn <= :page * 5
and   b.rn > (:page - 1) * 5;
-- PK_REPLY




-- ���.
create table tbl_reply (
  reply_no number  -- ��۹�ȣ
 ,replyer  varchar2(30) not null -- ����ۼ���
 ,reply    varchar2(1000) not null -- ��۳���
 ,board_no number not null -- ������ ��ȣ
 ,reply_date date default sysdate
);
alter table tbl_reply add constraint pk_reply primary key (reply_no);
create sequence reply_seq;

insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user01', '������ �������̳׿�1', 148);
insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user02', '������ �������̳׿�2', 148);
insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user03', '������ �������̳׿�3', 148);
insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user02', '������ �������̳׿�4', 148);

insert into tbl_reply (reply_no, replyer, reply, board_no)
select reply_seq.nextval, replyer, reply, board_no
from tbl_reply
where board_no = 148;



select *
from tbl_reply
where board_no = 148
order by 1;

delete from tbl_reply
where reply_no = 7;

delete from tbl_reply
where reply_no in (22, 23, 24, 25, 26);


select *
from tbl_board
order by 1 desc;


-- �Խ���
create table tbl_board (
  board_no number -- �Խñ� ��ȣ/ Ű
 ,title    varchar2(100) not null -- ������.
 ,content  varchar2(1000) not null -- �۳���.
 ,writer   varchar2(50) not null -- �ۼ���.
 ,view_cnt number default 0 -- ��ȸ��.
 ,creation_date  date default sysdate -- �Խñ� ��������.
);
alter table tbl_board add constraint pk_board primary key (board_no);
alter table tbl_board add image varchar2(100);
create sequence board_seq;

select *
from tbl_board
order by 1 desc;

delete from tbl_board
where board_no > 150;

insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '�Խñ� �׽�Ʈ', '�Խñ� �׽�Ʈ�Դϴ�', 'user01');
insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '�Խñ� Java �׽�Ʈ', '�Խñ� Java �׽�Ʈ�Դϴ�', 'user02');
insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '�Խñ� HTML �׽�Ʈ', '�Խñ� HTML �׽�Ʈ�Դϴ�', 'user03');

insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '�Խñ� ���� �׽�Ʈ', '�Խñ� ���� �׽�Ʈ�Դϴ�', 'chacha');

insert into tbl_board(board_no, title, content, writer)
select board_seq.nextval, title, content, writer
from tbl_board;

-- 1page -> 5�Ǿ� ���.
select c.*
from (select rownum rn, b.*
      from (select *
            from tbl_board
            order by board_no desc) b
      where rownum <= :page * 5 ) c
where c.rn > (:page - 1) * 5;

select /*+ INDEX_DESC (b PK_BOARD) */ b.*
from tbl_board b;

-- ȸ�����̺�.
create table tbl_member (
  member_id   varchar2(10) primary key
 ,member_name varchar2(100) not null
 ,password    varchar2(10) not null
 ,email       varchar2(50)
 ,authority   varchar2(10) default 'User' -- �Ϲݻ����: User, ������: Admin
 ,creation_date date default sysdate
);
insert into tbl_member (member_id, member_name, password)
values('user01', '��浿', '1111');
insert into tbl_member (member_id, member_name, password)
values('user02', '������', '1111');
insert into tbl_member (member_id, member_name, password)
values('user03', '�ֹμ�', '1111');
insert into tbl_member (member_id, member_name, password)
values('chacha', '������', '1111');

select *
from tbl_member;

insert into tbl_member (member_id, member_name, password, authority)
values('guest', '������', 'guest', 'Admin');

delete from tbl_member
where member_id = 'guest787';

drop table tbl_product purge;
create table tbl_product (
 prod_code varchar2(10) primary key -- ��ǰ�ڵ�(P001, P002)
,prod_name varchar2(100) not null -- ��ǰ��
,prod_desc varchar2(1000) not null -- ��ǰ����
,selling_price number -- ��ǰ����
,creation_date date default sysdate -- ������ �����Ͻ�
);

insert into tbl_product values('P001', '�𳪹� ���� 1.0', '���� ���� �𳪹� ���� 1.0 �Դϴ�', 1000, sysdate);
insert into tbl_product values('P002', '�𳪹� ���� 1.0', '���� ���� �𳪹� ���� 1.0 �Դϴ�', 2000, sysdate);
insert into tbl_product values('P003', '�𳪹� ���찳 1.0', '���� ���� �𳪹� ���찳 1.0 �Դϴ�', 500, sysdate);

select *
from tbl_product;
