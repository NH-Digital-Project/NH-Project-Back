-- 사용자
INSERT INTO users (
    id,
    naver_id,
    naver_name,
    applied,
    created_at,
    updated_at
) VALUES
      (
          1,
          'naver_1001',
          '김민수',
          true,
          NOW(),
          NOW()
      ),
      (
          2,
          'naver_1002',
          '박지연',
          false,
          NOW(),
          NOW()
      );
-- 관리자
INSERT INTO admins (
    id,
    login_id,
    admin_name,
    password,
    created_at
) VALUES
    (
        1,
        'admin',
        '황관리자',
        'admin1234',
        NOW()
    );

-- 지원서
-- INSERT INTO applications (
--     id,
--     user_id,
--     user_name,
--     birth_date,
--     phone_number,
--     application_number,
--     farm_name,
--     affiliated_nh_name,
--     farm_address,
--     business_registration_number,
--     main_product,
--     annual_sales,
--     online_distribution_experience,
--     product_category,
--     shipping_date,
--     funding_desired_date,
--     product_name,
--     product_size,
--     selling_price,
--     available_quantity,
--     funding_plan,
--     created_at,
--     updated_at
-- ) VALUES
--       (
--           1,
--           1,
--           '김민수',
--           '1995-03-12',
--           '010-1234-5678',
--           'APP-2026-0001',
--           '햇살농장',
--           '서울농협',
--           '경기도 남양주시',
--           '123-45-67890',
--           '사과',
--           50000000,
--           true,
--           '과일',
--           '2026-06-01',
--           '2026-06',
--           '프리미엄 사과 세트',
--           '5kg',
--           35000,
--           300,
--           '스마트 저장시설 구축 및 온라인 판매 확대 예정',
--           NOW(),
--           NOW()
--       ),
--       (
--           2,
--           2,
--           '박지연',
--           '1992-08-21',
--           '010-9876-5432',
--           'APP-2026-0002',
--           '푸른채소농장',
--           '부산농협',
--           '부산광역시 강서구',
--           '987-65-43210',
--           '상추',
--           30000000,
--           false,
--           '채소',
--           '2026-07-10',
--           '2026-07',
--           '친환경 채소 박스',
--           '3kg',
--           22000,
--           150,
--           '친환경 포장재 도입 예정',
--           NOW(),
--           NOW()
--       );
-- -- 선정업체
-- INSERT INTO projects (
--     id,
--     application_id,
--     farm_name,
--     product_category,
--     thumbnail_image_url,
--     description,
--     project_status,
--     happy_bean_url,
--     created_at,
--     updated_at
-- ) VALUES
--       (
--           1,
--           1,
--           '햇살농장',
--           '과일',
--           'https://example.com/images/apple-farm.jpg',
--           '친환경 재배 방식으로 생산한 고품질 사과 농장입니다.',
--           'IN_PROGRESS',
--           'https://happybean.naver.com/donations/H000000001',
--           NOW(),
--           NOW()
--       ),
--       (
--           2,
--           2,
--           '푸른채소농장',
--           '채소',
--           NULL,
--           '지역 농산물을 기반으로 운영하는 스마트팜입니다.',
--           'BEFORE_PROGRESS',
--           NULL,
--           NOW(),
--           NOW()
--       );