-- ====================================================================
-- 1. 사용자(User) 데이터 10개 삽입
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
-- 2. 지원서(Application) 데이터 10개 삽입 (날짜 형식 YYYY-MM-DD로 수정)
-- ====================================================================
INSERT IGNORE INTO applications (
    id,
    user_id,
    user_name,
    birth_date,
    phone_number,
    gender,
    application_number,
    farm_name,
    zipcode,
    street_address,
    detail_address,
    business_registration_number,
    agri_registration_number,
    main_product,
    annual_sales,
    online_distribution_experience,
    funding_experience,
    product_category,
    shipping_date,
    funding_desired_date,
    product_name,
    product_size,
    selling_price,
    available_quantity,
    motivation,
    funding_plan,
    status,
    created_at,
    updated_at
) VALUES
(1, 1, '김철수', '19900101', '010-1234-5678', '남성', '202605200001',
 '행복농장', '01234', '서울시 강남구', '101호',
 '123-45-67890', 'AGR-100001', '사과', 50000000, true, true,
 '과일', '9월 중순', '10월', '꿀사과', '5kg', 30000, 100,
 '온라인 판로 확대를 위해 지원했습니다.',
 '사과 재배 및 브랜드 홍보를 위한 펀딩 운영 계획입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 10 DAY),
 DATE_SUB(NOW(), INTERVAL 10 DAY)),

(2, 2, '이영희', '19850505', '010-9876-5432', '여성', '202605200002',
 '푸른채소농장', '12345', '경기도 수원시', '202호',
 '098-76-54321', 'AGR-100002', '상추', 30000000, false, false,
 '채소', '8월 초', '9월', '유기농 상추', '1kg', 15000, 200,
 '온라인 판매 경험이 부족하여 신청했습니다.',
 '친환경 상추 홍보 및 신규 고객 확보를 목표로 합니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 9 DAY),
 DATE_SUB(NOW(), INTERVAL 9 DAY)),

(3, 3, '박민수', '19780315', '010-1111-2222', '남성', '202605200003',
 '맑은한우농장', '23456', '강원도 횡성군', '본관',
 '111-22-33333', 'AGR-100003', '한우', 150000000, true, true,
 '축산', '연중출하', '11월', '명품 한우 세트', '2kg', 120000, 50,
 '프리미엄 한우 브랜드 강화를 위해 지원했습니다.',
 '한우 가공 및 패키지 개선을 위한 펀딩 계획입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 8 DAY),
 DATE_SUB(NOW(), INTERVAL 8 DAY)),

(4, 4, '최지우', '19920721', '010-3333-4444', '여성', '202605200004',
 '달콤배농원', '34567', '전남 나주시', 'A동',
 '222-33-44444', 'AGR-100004', '배', 60000000, false, false,
 '과일', '10월 초', '10월 말', '나주 신고배', '7.5kg', 45000, 80,
 '판로 확대와 신규 고객 확보가 필요합니다.',
 '배 선물세트 구성 및 온라인 홍보를 계획하고 있습니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 7 DAY),
 DATE_SUB(NOW(), INTERVAL 7 DAY)),

(5, 5, '정우성', '19801103', '010-5555-6666', '남성', '202605200005',
 '싱싱벌꿀', '45678', '충북 충주시', '1층',
 '333-44-55555', 'AGR-100005', '벌꿀', 25000000, false, true,
 '식음료', '7월', '8월', '천연 아카시아꿀', '1kg', 35000, 150,
 '온라인 판매 채널 확대를 위해 지원했습니다.',
 '브랜드 홍보와 상세페이지 제작에 활용 예정입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 6 DAY),
 DATE_SUB(NOW(), INTERVAL 6 DAY)),

(6, 6, '한지민', '19881005', '010-7777-8888', '여성', '202605200006',
 '황금귤농장', '56789', '제주도 서귀포시', '2층',
 '444-55-66666', 'AGR-100006', '감귤', 80000000, true, true,
 '과일', '11월 중순', '12월', '제주 조생감귤', '10kg', 25000, 300,
 '제주 감귤 브랜드 인지도 향상이 목표입니다.',
 '포장 개선 및 라이브커머스 운영을 계획하고 있습니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 5 DAY),
 DATE_SUB(NOW(), INTERVAL 5 DAY)),

(7, 7, '이정재', '19750220', '010-9999-0000', '남성', '202605200007',
 '바른버섯팜', '67890', '충남 부여군', '지하',
 '555-66-77777', 'AGR-100007', '표고버섯', 40000000, false, false,
 '채소', '9월 초', '9월 말', '생 표고버섯', '2kg', 28000, 120,
 '안정적인 온라인 판매처 확보가 필요합니다.',
 '버섯 가공 상품 개발 및 마케팅에 활용 예정입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 4 DAY),
 DATE_SUB(NOW(), INTERVAL 4 DAY)),

(8, 8, '송혜교', '19821122', '010-1234-1234', '여성', '202605200008',
 '구수한정미소', '78901', '경기도 이천시', '정미소',
 '666-77-88888', 'AGR-100008', '쌀', 200000000, true, true,
 '곡물', '10월 중순', '11월', '임금님표 이천쌀', '20kg', 65000, 500,
 '브랜드 가치 향상과 신규 고객 확보를 위해 신청했습니다.',
 '고급 패키지 제작 및 홍보 콘텐츠 제작 계획입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 3 DAY),
 DATE_SUB(NOW(), INTERVAL 3 DAY)),

(9, 9, '강동원', '19810118', '010-5678-5678', '남성', '202605200009',
 '새콤딸기농원', '89012', '충남 논산시', '비닐하우스',
 '777-88-99999', 'AGR-100009', '딸기', 70000000, true, false,
 '과일', '12월 말', '1월', '설향 딸기', '2kg', 32000, 200,
 '딸기 브랜드 온라인 홍보 확대가 필요합니다.',
 '신규 패키지 및 체험형 콘텐츠 제작을 계획 중입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 2 DAY),
 DATE_SUB(NOW(), INTERVAL 2 DAY)),

(10, 10, '유재석', '19720814', '010-1111-5555', '남성', '202605200010',
 '건강한포크', '90123', '경기도 안성시', 'A동',
 '888-99-00000', 'AGR-100010', '돼지고기', 300000000, true, true,
 '축산', '연중출하', '7월', '한돈 삼겹살 세트', '3kg', 55000, 400,
 '온라인 판매 경쟁력 강화를 위해 신청했습니다.',
 '축산물 브랜딩 및 홍보 콘텐츠 제작에 활용 예정입니다.',
 'APPROVED',
 DATE_SUB(NOW(), INTERVAL 1 DAY),
 DATE_SUB(NOW(), INTERVAL 1 DAY));

-- ====================================================================
-- 3. 선정업체(Project) 데이터 10개 삽입 (sort_order 추가)
-- ====================================================================
INSERT IGNORE INTO projects (
    id, application_id, farm_name, product_category, thumbnail_image_url,
    description, project_status, happy_bean_url, sort_order, created_at, updated_at
) VALUES
(1, 1, '행복농장', '과일', 'https://example.com/images/apple.jpg', '친환경 사과 재배 농가입니다.', 'IN_PROGRESS', 'https://happybean.naver.com/donations/H000000001', 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(4, 4, '달콤배농원', '과일', 'https://example.com/images/pear.jpg', '아삭하고 달콤한 신고배를 재배합니다.', 'IN_PROGRESS', 'https://happybean.naver.com/donations/H000000004', 2, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(9, 9, '새콤딸기농원', '과일', NULL, '논산에서 자란 신선한 설향 딸기입니다.', 'IN_PROGRESS', 'https://happybean.naver.com/donations/H000000009', 3, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 2, '푸른채소농장', '채소', NULL, '스마트팜을 이용한 신선한 상추 재배.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000002', 1, DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(5, 5, '싱싱벌꿀', '식음료', 'https://example.com/images/honey.jpg', '자연 그대로의 순수 아카시아 벌꿀입니다.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000005', 2, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(7, 7, '바른버섯팜', '채소', 'https://example.com/images/mushroom.jpg', '무농약으로 안전하게 키운 표고버섯입니다.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000007', 3, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(10, 10, '건강한포크', '축산', 'https://example.com/images/pork.jpg', '엄격하게 관리된 최고급 한돈 삼겹살.', 'BEFORE_PROGRESS', 'https://happybean.naver.com/donations/H000000010', 4, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, 3, '맑은한우농장', '축산', 'https://example.com/images/beef.jpg', '강원도 횡성 명품 한우 가공 농가입니다.', 'COMPLETED', 'https://happybean.naver.com/donations/H000000003', 1, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(6, 6, '황금귤농장', '과일', NULL, '제주 햇살을 가득 머금은 새콤달콤 감귤.', 'COMPLETED', 'https://happybean.naver.com/donations/H000000006', 2, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(8, 8, '구수한정미소', '곡물', 'https://example.com/images/rice.jpg', '윤기 흐르고 찰진 최고급 이천 쌀입니다.', 'COMPLETED', 'https://happybean.naver.com/donations/H000000008', 3, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY));