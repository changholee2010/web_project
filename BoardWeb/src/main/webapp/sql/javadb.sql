-- fullcalendar 데이터.
create table tbl_calendar (
  title varchar2(1000) not null
 ,start_date varchar2(20) not null
 ,end_date varchar2(20)
);

insert into tbl_calendar values('회의1', '2024-09-12T16:00:00', '2024-09-12T18:00:00');
insert into tbl_calendar values('추석연휴', '2024-09-16', '2024-09-19');

select *
from tbl_calendar;

delete from tbl_calendar
where title like '회의1';

create table tbl_todo (
  todo_id number primary key,
  todo_date varchar2(20) not null, -- '2024-09-08'
  todo_title varchar2(300) not null,
  creation_date date default sysdate
);
create sequence todo_seq;

insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-13', '구글 차트');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-13', '장바구니 구현');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-13', '자바 클래스 인터페이스');

insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-12', '자바 클래스2');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-12', '자바 클래스 상속2');
insert into tbl_todo (todo_id, todo_date, todo_title)
values(todo_seq.nextval, '2024-09-12', '자바 클래스 인터페이스2');


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




-- 댓글.
create table tbl_reply (
  reply_no number  -- 댓글번호
 ,replyer  varchar2(30) not null -- 댓글작성자
 ,reply    varchar2(1000) not null -- 댓글내용
 ,board_no number not null -- 원본글 번호
 ,reply_date date default sysdate
);
alter table tbl_reply add constraint pk_reply primary key (reply_no);
create sequence reply_seq;

insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user01', '오늘은 수요일이네요1', 148);
insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user02', '오늘은 수요일이네요2', 148);
insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user03', '오늘은 수요일이네요3', 148);
insert into tbl_reply (reply_no, replyer, reply, board_no)
values (reply_seq.nextval, 'user02', '오늘은 수요일이네요4', 148);

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


-- 게시판
create table tbl_board (
  board_no number -- 게시글 번호/ 키
 ,title    varchar2(100) not null -- 글제목.
 ,content  varchar2(1000) not null -- 글내용.
 ,writer   varchar2(50) not null -- 작성자.
 ,view_cnt number default 0 -- 조회수.
 ,creation_date  date default sysdate -- 게시글 생성일자.
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
values(board_seq.nextval, '게시글 테스트', '게시글 테스트입니다', 'user01');
insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '게시글 Java 테스트', '게시글 Java 테스트입니다', 'user02');
insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '게시글 HTML 테스트', '게시글 HTML 테스트입니다', 'user03');

insert into tbl_board (board_no, title, content, writer)
values(board_seq.nextval, '게시글 도서 테스트', '게시글 도서 테스트입니다', 'chacha');

insert into tbl_board(board_no, title, content, writer)
select board_seq.nextval, title, content, writer
from tbl_board;

-- 1page -> 5건씩 출력.
select c.*
from (select rownum rn, b.*
      from (select *
            from tbl_board
            order by board_no desc) b
      where rownum <= :page * 5 ) c
where c.rn > (:page - 1) * 5;

select /*+ INDEX_DESC (b PK_BOARD) */ b.*
from tbl_board b;

-- 회원테이블.
create table tbl_member (
  member_id   varchar2(10) primary key
 ,member_name varchar2(100) not null
 ,password    varchar2(10) not null
 ,email       varchar2(50)
 ,authority   varchar2(10) default 'User' -- 일반사용자: User, 관리자: Admin
 ,creation_date date default sysdate
);
insert into tbl_member (member_id, member_name, password)
values('user01', '김길동', '1111');
insert into tbl_member (member_id, member_name, password)
values('user02', '유영석', '1111');
insert into tbl_member (member_id, member_name, password)
values('user03', '최민석', '1111');
insert into tbl_member (member_id, member_name, password)
values('chacha', '차차차', '1111');

select *
from tbl_member;

insert into tbl_member (member_id, member_name, password, authority)
values('guest', '관리자', 'guest', 'Admin');

delete from tbl_member
where member_id = 'guest787';

drop table tbl_product purge;
create table tbl_product (
 prod_code varchar2(10) primary key -- 상품코드(P001, P002)
,prod_name varchar2(100) not null -- 상품명
,prod_desc varchar2(1000) not null -- 상품설명
,selling_price number -- 상품가격
,creation_date date default sysdate -- 데이터 생성일시
);

insert into tbl_product values('P001', '모나미 볼펜 1.0', '아주 좋은 모나미 볼펜 1.0 입니다', 1000, sysdate);
insert into tbl_product values('P002', '모나미 샤프 1.0', '아주 좋은 모나미 샤프 1.0 입니다', 2000, sysdate);
insert into tbl_product values('P003', '모나미 지우개 1.0', '아주 좋은 모나미 지우개 1.0 입니다', 500, sysdate);

select *
from tbl_product;
