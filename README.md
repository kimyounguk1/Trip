<h1>Trip Plus</h1>
<img width="150" height="300" alt="image" src="https://github.com/user-attachments/assets/f4e026c2-6b29-4200-9cd8-0d8bc7dd44fa" />
<img width="150" height="300" alt="image" src="https://github.com/user-attachments/assets/da7e2cdf-1cc9-427e-8cd4-7e1eacff4063" />
<img width="150" height="300" alt="image" src="https://github.com/user-attachments/assets/7d4261bf-e280-42e5-9679-0480c024cd11" />
<img width="150" height="300" alt="image" src="https://github.com/user-attachments/assets/ffeac0f6-6b9c-4bb1-9b88-d5c84acaf047" />
<br>
<img width="681" height="415" alt="image" src="https://github.com/user-attachments/assets/ec8b8a5f-b63d-4289-841a-6187e1734f3b" />
<br>
<a href="https://github.com/Team-Deepin">원본 프로젝트로 이동</a>
<P>Trip Plus는 AI 를 활용한 여행코스 추천 앱입니다.<br>
현재 리포지토리는 해당 프로젝에서 제가 맡은 부분 및 추가로 발전시킨 부분을 담고있습니다.</P>
<h1>Stack</h1>
<!-- Backend -->
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
<img src="https://img.shields.io/badge/OAuth2-2FADFF?style=for-the-badge&logo=oauth&logoColor=white">

<!-- Infra -->
<img src="https://img.shields.io/badge/AWS%20EKS-FF9900?style=for-the-badge&logo=amazon-eks&logoColor=white">
<img src="https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white">
<img src="https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white">

<!-- Monitoring & Caching -->
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">
<img src="https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">

<!-- Others -->
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<h1>Features</h1>
<ul>
  <li>공공데이터 포털의 데이터를 학습한 ML 모델로 여행지 추천</li>
  <li>선호 여행지 옵션 다중 선택 처리 가능</li>
  <li>QueryDSL을 통한 유동적인 쿼리 생성 및 최적화</li>
  <li>JWT 기반 Spring Security를 활용하여 사용자의 로그인 및 인증 처리</li>
  <li>OAuth2를 활용한 소셜 로그인 구현</li>
  <li>EC2 인스턴스를 활용하여 프로덕션 환경 구축, Docker Compose, Kubernetes를 사용한 배포</li>
  <li>tmap api로 대중교통, 자가용에 따른 실시간 이동 방법 제공</li>
</ul>
<h1>
  architecture
</h1>
개선전 구조
<br>
<img width="716" height="496" alt="image" src="https://github.com/user-attachments/assets/32787b6d-566b-4c9e-a933-572f28b601bf" />
<br>
개선후 구조
<br>
<img width="662" height="438" alt="image" src="https://github.com/user-attachments/assets/b8a4338d-d84b-4fbc-a984-c31710920b67" />
<h1>PROBLEM-SOLVING</h1>
1. JWT 토큰의 보안 문제

> JWT 토큰이 클라이언트에 저장되면서 XSS, CSRF 등 보안 문제가 발생함
> 
- 사용 빈도가 높은 Access 토큰은 탈취 가능성이 높음으로 짧은 유효 기간을 갖게 하며 의심스러운 접근 시 로그아웃 처리 또한, 클라이언트의 로컬 환경에 저장하여 상대적으로 방어 용이한 XSS 공격의 보안 조치를 강화함
- Refresh 토큰은 HttpOnly 쿠키, HTTPS를 사용하여 XSS, MITM 공격 보안 강화

2. Redis 동시성 문제

> 동시에 여러 사용자가 같은 콘텐츠를 조회할 때, 조회수 증가 연산이 겹쳐져 일부 업데이트가 유실되는 레이스 컨디션이 발생
> 
- Redis의 WATCH, MULTI, EXEC명령어를 활용하여 낙관적 락(Optimistic Lock)을 적용
- 트랜잭션 실패에 대한 재시도 로직 구현으로 일관성 확보

3. Spring 서비스 장애 문제

> Docker Compose와 EC2 인스턴스를 이용해 서비스를 배포하던 중, EC2의 메모리 리소스 부족으로 인해 애플리케이션 서버가 다운되는 문제가 발생함
> 
- EKS 기반 Kubernetes 클러스터에 Spring Boot 애플리케이션을 배포, Pod 자동 복구 설정으로 서비스 유지
- AWS의 ALB(Application Load Balancer)와 Kubernetes의 Service를 연동하여 트래픽 부하를 최소화
