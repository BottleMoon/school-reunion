# -- School 데이터 삽입
# INSERT INTO school (id, name) VALUES (1, 'School A');
# INSERT INTO school (id, name) VALUES (2, 'School B');
#
# -- Member 데이터 삽입
# INSERT INTO member (id, email, name) VALUES (1, 'member1@example.com', 'Member 1');
# INSERT INTO member (id, email, name) VALUES (2, 'member2@example.com', 'Member 2');
#
# -- SchoolMember 데이터 삽입
# INSERT INTO school_member (id, school_id, member_id) VALUES (1, 1, 1);
# INSERT INTO school_member (id, school_id, member_id) VALUES (2, 2, 2);
#
# -- Reunion 데이터 삽입
# INSERT INTO reunion (id, school_id, grade, year) VALUES (1, 1, 10, 2023);
# INSERT INTO reunion (id, school_id, grade, year) VALUES (2, 2, 12, 2022);
#
# -- GradeYear 데이터 삽입
# INSERT INTO grade_year (id, school_member_id, grade, year) VALUES (1, 1, 10, 2023);
# INSERT INTO grade_year (id, school_member_id, grade, year) VALUES (2, 2, 12, 2022);

-- Board 데이터 삽입
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (1, 1, 1, '우리 동호회의 새로운 회원 소개', '안녕하세요! 새로 오신 분들을 환영합니다. 간단한 자기 소개와 함께 어떤 분야에 관심이 있는지 알려주세요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (2, 2, 2, '다음 모임 일정 안내', '다음 주 모임 일정을 공지합니다. 모두의 일정을 조율하여 최적의 날짜를 선정해보려 합니다. 가능한 날짜를 빠르게 공유해주세요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (3, 2, 2, '동호회 회칙 개정 안내', '동호회 회칙을 개정하려 합니다. 함께 논의하여 어떠한 부분을 수정할지 의견을 주시면 감사하겠습니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (4, 1, 1, '취미 공유하기: 나만의 특별한 방법', '각자의 취미를 소개하고 나만의 특별한 방법을 공유해보세요! 다른 회원들에게 영감을 줄 수 있는 좋은 기회가 될 것입니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (5, 2, 2, '동호회 여행 계획 모집', '다음 여행을 준비하고 있습니다. 함께 가실 분들을 찾고 있으니 관심 있는 분들은 댓글로 참여 의사를 알려주세요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (6, 1, 1, '최근 인기 있는 책 소개', '최근에 읽은 책 중 인상 깊었던 책을 소개해보세요. 다양한 책에 대한 의견을 나누며 동호회 멤버들과 함께하는 책토론!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (7, 2, 2, '공동구매 안내', '공동구매를 진행하려 합니다. 관심 있는 제품이 있거나 함께 구매하고 싶은 제품이 있다면 댓글로 알려주세요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (8, 1, 1, '새로운 동호회 로고 공모전', '동호회 로고를 새롭게 디자인하려 합니다. 참여하고 싶은 분들은 자신의 아이디어를 공유해주세요. 최고의 디자인이 선택될 것입니다!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (9, 1, 1, '취미에 관한 질문 모음', '취미에 관련된 질문이 있으신가요? 함께 토론하고 조언을 구해보세요. 동호회 멤버들이 서로 도와줄 준비가 되어 있습니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (10, 2, 2, '주말 야외 활동 계획 모집', '다음 주말에 야외 활동을 계획하고 있습니다. 함께 하실 분들을 모집합니다. 자연 속에서 즐거운 시간을 보내요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (11, 1, 1, '프로젝트 팀원 모집', '새로운 프로젝트를 준비하고 있습니다. 함께 참여하실 분들을 모집합니다. 역할과 관심사를 댓글로 남겨주세요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (12, 1, 1, '테마 별 취미 모임 제안', '다양한 테마에 맞는 취미 모임을 제안해보세요. 함께 즐길 수 있는 새로운 아이디어를 기다립니다!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (13, 1, 1, '동호회 봉사활동 참여 안내', '동호회에서 봉사활동에 참여하실 분들을 모집합니다. 소중한 시간을 함께 나누며 더 의미 있는 활동을 만들어봐요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (14, 2, 2, '일상 이야기 공유하기', '오늘의 소소한 일상 이야기를 함께 공유해보세요. 함께 웃고 공감하는 재미있는 대화가 펼쳐질 것입니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (15, 2, 2, '취미 활동 후기 공유', '최근에 즐겼던 취미 활동에 대한 후기를 공유해주세요. 다른 회원들에게 유용한 정보가 될 것입니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (16, 1, 1, '음악 추천 모음', '최근에 들은 좋은 음악이 있다면 공유해주세요! 다양한 음악을 통해 취향을 공유하고 함께 음악 여행을 떠나보세요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (17, 1, 1, '요리 레시피 공유', '요리에 관심 있는 회원들끼리 레시피를 공유해보세요. 맛있는 요리로 함께 즐거운 시간을 보낼 수 있을 거예요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (18, 2, 2, '건강한 라이프스타일 팁 공유', '건강한 라이프스타일을 위한 다양한 팁을 공유해주세요. 함께 건강하고 행복한 삶을 만들어가요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (19, 1, 1, '동호회 인기 게임 대회 개최 안내', '다음 주에 인기 게임 대회를 개최합니다. 참여하실 분들은 미리 신청해주세요. 함께 열정을 불태워보아요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (20, 1, 1, '영화 추천 공유', '최근 본 영화 중 추천하고 싶은 작품이 있다면 소개해주세요. 다양한 영화를 통해 감성을 공유해보아요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (21, 2, 2, '동호회 미니 공모전 개최 안내', '다양한 주제로 미니 공모전을 개최합니다. 참여하고 싶으신 분들은 자유롭게 참여해주세요. 멋진 작품들이 기다리고 있습니다!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (22, 1, 1, '취미 관련 정보 공유', '취미에 관련된 유용한 정보를 공유해보세요. 다양한 활동을 통해 함께 성장하고 즐거움을 나누어요.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (23, 1, 1, '동호회 소식 및 공지사항 안내', '동호회의 소식과 공지사항을 안내드립니다. 회원 여러분들의 참여와 관심을 부탁드립니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (24, 2, 2, '문화체험 모임 참여자 모집', '문화체험 모임을 준비하고 있습니다. 함께 문화를 체험하고 나누는 소중한 시간을 함께 만들어봐요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (25, 2, 2, '건의사항 및 개선 의견 모집', '동호회의 발전을 위해 건의사항과 개선 의견을 모집합니다. 회원 여러분의 소중한 의견을 기다립니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (26, 1, 1, '동호회 멤버들의 추억 공유', '함께한 추억 속 이야기를 공유해보세요. 함께 한 시간들을 회상하며 더욱 뜻깊은 동호회가 되어봐요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (27, 2, 2, '취미 활동 중 필요한 장비 공유', '취미 활동을 위해 필요한 장비를 공유하고 나누어보세요. 함께 활동을 즐기는데 도움이 될 것입니다.');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (28, 1, 1, '동호회 멤버들의 인생 명언 공유', '인생에서 영감을 준 명언을 공유해보세요. 서로에게 용기를 주고 긍정적인 에너지를 나누어봐요!');
INSERT INTO board (id, reunion_id, member_id, title, content)
VALUES (29, 1, 1, '다양한 이벤트 및 축제 참여 안내', '다가오는 이벤트 및 축제에 참여하실 분들을 모집합니다. 함께 즐거운 시간을 보내요!');