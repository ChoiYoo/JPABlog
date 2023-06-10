INSERT INTO MEMBER (ID, EMAIL, PASSWORD, PHONE, REG_DATE, UPDATE_DATE, USER_NAME, STATUS, LOCK_YN, PASSWORD_RESET_YN)
VALUES (1, 'test@gmail.com', '$2a$10$aVjtWrojTOJJStC5EFH6Z.nG/U4c0PXJke3pmYWElmS/M77dG3Ud2', '010-1111-2222', '2021-02-01 00:49:43.000000', null, '박규태', 1, 0, 0)
     , (2, 'test1@gmail.com', '$2a$10$8bcV6o.TrTPAPzO7N7Q7YOaC3wFql99nw0Doy/F.AtNvc5ehTRCqu', '010-3333-4444', '2021-02-19 00:50:11.000000', null, '정혜경', 1, 0, 0)
     , (3, 'test2@gmail.com', '3333', '010-5555-6666', '2021-02-19 23:27:07.000000', null, '박하은', 1, 0, 0)
     , (4, 'test3@gmail.com', '4444', '010-7777-9999', '2021-02-02 00:27:51.000000', null, '박하영', 2, 0, 0);


INSERT INTO NOTICE (ID, CONTENTS, DELETED_DATE, DELETED, HITS, LIKES, REG_DATE, TITLE, UPDATE_DATE, MEMBER_ID)
VALUES (2, '내용2', null, false, 0, 0, '2021-02-01 01:12:37.000000', '제목2', null, 1)
     , (1, '내용1', null, false, 0, 0, '2021-02-01 01:12:20.000000', '제목1', null, 1)
     , (3, '내용3', null, false, 0, 0, '2021-02-01 01:13:07.000000', '제목3', null, 2)
     , (4, '내용4', null, false, 0, 0, '2021-02-01 01:13:10.000000', '제목4', null, 2)
     , (5, '내용5', null, false, 0, 0, '2021-02-01 01:13:12.000000', '제목5', null, 2)
     , (6, '내용6', null, false, 0, 0, '2021-02-01 23:31:23.000000', '제목6', null, 1)
     , (7, '내용7', null, false, 0, 0, '2021-02-01 23:31:26.000000', '제목7', null, 3)
     , (9, '내용9', null, false, 0, 0, '2021-02-01 23:31:35.000000', '제목9', null, 1)
     , (10, '내용10', null, false, 0, 0, '2021-02-01 23:31:38.000000', '제목10', null, 1);

INSERT INTO NOTICE_LIKE (ID, NOTICE_ID, MEMBER_ID)
VALUES (1, 3, 1)
     , (2, 4, 1)
     , (3, 1, 1)
     , (4, 3, 2)
     , (5, 1, 4)
     , (6, 2, 4);


INSERT INTO BOARD_TYPE (ID, BOARD_NAME, REG_DATE, USING_YN)
VALUES (1, '게시판1', '2021-02-01 01:12:37.000000', 1)
     , (2, '게시판2', '2021-02-01 01:12:37.000000', 1);


INSERT INTO BOARD (ID, BOARD_TYPE_ID, MEMBER_ID, TITLE, CONTENTS, REG_DATE, TOP_YN)
VALUES (1, 1, 1, '게시글1', '게시글내용1', '2021-02-01 01:12:37.000000', 0)
     , (2, 1, 1, '게시글2', '게시글내용2', '2021-02-01 01:12:37.000000', 0);

INSERT INTO BOARD_COMMENT(ID, COMMENTS, REG_DATE, BOARD_ID, MEMBER_ID)
VALUES
(1, '게시글1번에 대한 댓글1', '2021-02-01 01:12:37.000000', 1, 1)
     , (2, '게시글1번에 대한 댓글2', '2021-02-01 01:12:37.000000', 1, 1)
     , (3, '게시글2번에 대한 댓글1', '2021-02-01 01:12:37.000000', 2, 1)
     , (4, '게시글1번에 대한 댓글3', '2021-02-01 01:12:37.000000', 1, 2)
;


INSERT INTO MAIL_TEMPLATE(ID, TEMPLATE_ID, TITLE, CONTENTS, SEND_EMAIL, SEND_USER_NAME, REG_DATE)
VALUES (1, 'USER_RESET_PASSWORD'
       , '{MEMBER_NAME}님의 비밀번호 초기화 요청입니다.'
       , '<div><p>{MEMBER_NAME}님 안녕하세요.</p><p>아래 링크를 클릭하여, 비밀번호를 초기화해 주세요.</p><p><a href="{SERVER_URL}/reset?key={RESET_PASSWORD_KEY}">초기화</a></p></div>'
       , '', '관리자', '2021-02-01 01:12:37.000000')
     , (2, 'BOARD_ADD'
       , '{MEMBER_NAME}님이 글을 게시하였습니다.'
       , '<div><p>제목: {BOARD_TITLE}</p><p>내용</p><div>{BOARD_CONTENTS}</div></div>'
       , '', '관리자', '2021-02-01 01:12:37.000000')
     , (3, 'BOARD_REPLY'
       , '{MEMBER_NAME}님이 글에 답변을 하였습니다.'
       , '<div><p>제목: {BOARD_TITLE}</p><p>내용</p><div>{BOARD_CONTENTS}</div><p>답변</p><div>{BOARD_REPLY_CONTENTS}</div></div>'
       , '', '관리자', '2021-02-01 01:12:37.000000')
     , (4, 'USER_SERVICE_NOTICE'
       , '{MEMBER_NAME}님 안녕하세요.'
       , '<div><p>개인정보 이용내역 안내</p><p>서비스를 잘 이용하고 계십니다.</p></div>'
       , '', '관리자', '2021-02-01 01:12:37.000000')
;

INSERT INTO LOGS (ID, TEXT, REG_DATE)
VALUES
(1, '로그1', '2021-03-01 01:01:01.000000')
     , (2, '로그2', '2021-03-01 01:02:01.000000')
     , (3, '로그3', '2021-03-01 01:03:01.000000')
     , (4, '로그4', '2021-03-01 01:04:01.000000')
     , (5, '로그5', '2021-03-01 01:05:01.000000')
;