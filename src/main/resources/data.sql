-- ====================================================================
-- 1. 사용자(User) 데이터 10개 삽입 (변경됨: oauth_id, user_name, email, role 추가)
-- ====================================================================
INSERT IGNORE INTO users (id, oauth_id, user_name, email, role, applied, created_at, updated_at) VALUES
(1, 'naver_user_001', '김철수', 'chulsoo@example.com', 'ROLE_USER', false, NOW(), NOW()),
(2, 'naver_user_002', '이영희', 'younghee@example.com', 'ROLE_USER', false, NOW(), NOW()),
(3, 'naver_user_003', '박민수', 'minsoo@example.com', 'ROLE_USER', false, NOW(), NOW()),
(4, 'naver_user_004', '최지우', 'jiwoo@example.com', 'ROLE_USER', false, NOW(), NOW()),
(5, 'naver_user_005', '정우성', 'woosung@example.com', 'ROLE_USER', false, NOW(), NOW()),
(6, 'naver_user_006', '한지민', 'jimin@example.com', 'ROLE_USER', false, NOW(), NOW()),
(7, 'naver_user_007', '이정재', 'jungjae@example.com', 'ROLE_USER', false, NOW(), NOW()),
(8, 'naver_user_008', '송혜교', 'hyekyo@example.com', 'ROLE_USER', false, NOW(), NOW()),
(9, 'naver_user_009', '강동원', 'dongwon@example.com', 'ROLE_USER', false, NOW(), NOW()),
(10, 'naver_user_010', '유재석', 'jaeseok@example.com', 'ROLE_USER', false, NOW(), NOW());

INSERT IGNORE INTO admins(id, login_id, admin_name, password, role, created_at, updated_at) VALUES
(1, 'test', '최유림', '$2b$12$SYwFjnQ9RoY6SCPEFnbFMeJmFB1oRN3BLc/qJcU8P1Oz5qyfIUffS', 'ROLE_ADMIN', NOW(), NOW());
-- ====================================================================
-- 2. 지원서(Application) 데이터 10개 삽입 (동일)
-- ====================================================================
INSERT IGNORE INTO applications (
    id, user_id, user_name, birth_date, phone_number, application_number,
    farm_name, affiliated_nh_name, zipcode, street_address, detail_address,
    business_registration_number, main_product, annual_sales, online_distribution_experience,
    product_category, shipping_date, funding_desired_date, product_name, product_size,
    selling_price, available_quantity, funding_plan, status, created_at, updated_at
) VALUES
(1, 1, '김철수', '19900101', '010-1234-5678', '202605200001', '행복농장', '서울농협', '01234', '서울시 강남구', '101호', '123-45-67890', '사과', 50000000, true, '과일', '9월 중순', '10월', '꿀사과', '5kg', 30000, 100, '사과 재배 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 2, '이영희', '19850505', '010-9876-5432', '202605200002', '푸른채소농장', '경기농협', '12345', '경기도 수원시', '202호', '098-76-54321', '상추', 30000000, false, '채소', '8월 초', '9월', '유기농 상추', '1kg', 15000, 200, '상추 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(3, 3, '박민수', '19780315', '010-1111-2222', '202605200003', '맑은한우농장', '횡성농협', '23456', '강원도 횡성군', '본관', '111-22-33333', '한우', 150000000, true, '축산', '연중출하', '11월', '명품 한우 세트', '2kg', 120000, 50, '한우 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(4, 4, '최지우', '19920721', '010-3333-4444', '202605200004', '달콤배농원', '나주농협', '34567', '전남 나주시', 'A동', '222-33-44444', '배', '60000000', false, '과일', '10월 초', '10월 말', '나주 신고배', '7.5kg', 45000, 80, '배 재배 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(5, 5, '정우성', '19801103', '010-5555-6666', '202605200005', '싱싱벌꿀', '충주농협', '45678', '충북 충주시', '1층', '333-44-55555', '벌꿀', 25000000, false, '식음료', '7월', '8월', '천연 아카시아꿀', '1kg', 35000, 150, '벌꿀 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(6, 6, '한지민', '19881005', '010-7777-8888', '202605200006', '황금귤농장', '제주농협', '56789', '제주도 서귀포시', '2층', '444-55-66666', '감귤', 80000000, true, '과일', '11월 중순', '12월', '제주 조생감귤', '10kg', 25000, 300, '감귤 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(7, 7, '이정재', '19750220', '010-9999-0000', '202605200007', '바른버섯팜', '부여농협', '67890', '충남 부여군', '지하', '555-66-77777', '표고버섯', 40000000, false, '채소', '9월 초', '9월 말', '생 표고버섯', '2kg', 28000, 120, '버섯 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(8, 8, '송혜교', '19821122', '010-1234-1234', '202605200008', '구수한정미소', '이천농협', '78901', '경기도 이천시', '정미소', '666-77-88888', '쌀', 200000000, true, '곡물', '10월 중순', '11월', '임금님표 이천쌀', '20kg', 65000, 500, '이천쌀 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
(9, 9, '강동원', '19810118', '010-5678-5678', '202605200009', '새콤딸기농원', '논산농협', '89012', '충남 논산시', '비닐하우스', '777-88-99999', '딸기', 70000000, true, '과일', '12월 말', '1월', '설향 딸기', '2kg', 32000, 200, '딸기 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(10, 10, '유재석', '19720814', '010-1111-5555', '202605200010', '건강한포크', '도드람농협', '90123', '경기도 안성시', 'A동', '888-99-00000', '돼지고기', 300000000, true, '축산', '연중출하', '7월', '한돈 삼겹살 세트', '3kg', 55000, 400, '한돈 펀딩', 'APPROVED', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));


-- ====================================================================
-- 3. 선정업체(Project) 데이터 10개 삽입 (동일)
-- ====================================================================
INSERT IGNORE INTO projects (
    id, application_id, farm_name, product_category, thumbnail_image_url,
    description, project_status, happy_bean_url, created_at, updated_at
) VALUES
(1, 1, '행복농장', '과일', 'https://example.com/images/apple.jpg', '친환경 사과 재배 농가입니다.', 'IN_PROGRESS', 'https://happybean.naver.com/donations/H000000001', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(4, 4, '달콤배농원', '과일', 'https://example.com/images/pear.jpg', '아삭하고 달콤한 신고배를 재배합니다.', 'IN_PROGRESS', 'https://happybean.naver.com/donations/H000000004', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(9, 9, '새콤딸기농원', '과일', NULL, '논산에서 자란 신선한 설향 딸기입니다.', 'IN_PROGRESS', 'https://happybean.naver.com/donations/H000000009', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 2, '푸른채소농장', '채소', NULL, '스마트팜을 이용한 신선한 상추 재배.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000002', DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(5, 5, '싱싱벌꿀', '식음료', 'https://example.com/images/honey.jpg', '자연 그대로의 순수 아카시아 벌꿀입니다.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000005', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(7, 7, '바른버섯팜', '채소', 'https://example.com/images/mushroom.jpg', '무농약으로 안전하게 키운 표고버섯입니다.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000007', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(10, 10, '건강한포크', '축산', 'https://example.com/images/pork.jpg', '엄격하게 관리된 최고급 한돈 삼겹살.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000010', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, 3, '맑은한우농장', '축산', 'https://example.com/images/beef.jpg', '강원도 횡성 명품 한우 가공 농가입니다.', 'COMPLETED', 'https://happybean.naver.com/donations/H000000003', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(6, 6, '황금귤농장', '과일', NULL, '제주 햇살을 가득 머금은 새콤달콤 감귤.', 'COMPLETED', 'https://happybean.naver.com/donations/H000000006', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(8, 8, '구수한정미소', '곡물', 'https://example.com/images/rice.jpg', '윤기 흐르고 찰진 최고급 이천 쌀입니다.', 'COMPLETED', 'https://happybean.naver.com/donations/H000000008', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY));
