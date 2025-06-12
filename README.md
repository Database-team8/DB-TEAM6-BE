
---
<h1 align="left">🧭 AjouFinder - 분실물 반환 플랫폼 (Backend)</h1>

<p align="center">
  <img src="https://github.com/user-attachments/assets/b93fe352-9545-40a7-93cd-337a316023fa" width="160" alt="AjouFinder 로고" />
</p>

  <tr>
    <td style="vertical-align: middle; border: none;">
      <p>
        캠퍼스에서 지갑을 잃어버렸는데 어디에 문의해야 할지 몰라 허둥댄 적 있나요?<br />
        수많은 분실물 공지는 있지만, 정작 내 물건은 어디 있는지 찾기 어려웠던 기억이 있나요?<br /><br />
        <b>AjouFinder</b>는 아주대학교 학생들이 겪는 이런 현실적인 불편함에서 출발한 서비스입니다.<br />
        교내 곳곳에 흩어진 분실물 정보, 개인정보가 그대로 노출된 게시글, 검색이 되지 않는 포털 시스템.<br />
        이러한 문제들을 해결하고자 <b>표준화된 게시 시스템, 실시간 상태 업데이트, 조건 기반 알림, 지도 기반 탐색</b> 기능을 한 곳에 모았습니다.<br /><br />
        이제 분실자와 습득자는 서로를 쉽게 찾고, 안전하게 연결되며, 더 이상 물건을 찾기 위해 이곳저곳 헤매지 않아도 됩니다.
      </p>
  </tr>

## 🔗관련 링크
- 🔧 API 명세 (Swagger UI): [http://43.203.202.76:8080/swagger-ui/index.html](http://43.203.202.76:8080/swagger-ui/index.html)
- 📂 전체 저장소: [GitHub Organization - Database-team8](https://github.com/Database-team8)

> 🔸 현재 프론트엔드 서비스는 배포되지 않았으며, API 명세는 Swagger UI를 통해 확인하실 수 있습니다.

---
## 📌 주요 기능

<table>
  <tr>
   <td width="45%" align="center" valign="middle">  
      <img src="https://github.com/user-attachments/assets/7233f370-9207-4930-90d3-1634ac35ce3b" alt="탭별 게시글 조회" width="180" />
    </td>
   <td width="55%" valign="middle">
      <h3>📍 잃었어요 / 주웠어요 탭</h3>
      <ul>
        <li>분실 / 습득 게시글을 나눠서 조회 가능</li>
        <li>필터 기능으로 원하는 게시글만 선택</li>
        <li>하단 버튼으로 손쉽게 게시글 작성</li>
      </ul>
    </td>
  </tr>
  <tr>
      <td width="45%" align="center" valign="middle">
      <img src="https://github.com/user-attachments/assets/c7be1928-a467-400e-bac8-5d265c766b83" alt="게시글 상세" width="180" />
    </td>
    <td width="55%" valign="middle">
      <h3>🔍 게시글 상세 페이지 </h3>
      <ul>
        <li>작성자, 제목, 내용, 분실 위치 및 시간 등 상세 정보 확인</li>
        <li>작성자는 게시글 수정 / 삭제 가능</li>
        <li>반환 완료 시 상태 변경 가능 (‘해결 완료’)</li>
      </ul>
    </td>
  </tr>
<tr>
 <td width="45%" align="center" valign="middle">
    <img src="https://github.com/user-attachments/assets/924fb589-0e93-40f3-b88b-6f5cda81202b" width="180" alt="게시글 작성 기능" />
  </td>
  <td width="55%" valign="middle">
    <h3>📝 게시글 작성 기능</h3>
    <ul>
      <li>분실/습득 장소, 종류, 일시 등 표준화된 양식 제공</li>
      <li>필수 정보 입력을 유도해 검색 정확도 향상</li>
      <li>직관적인 UI로 빠른 작성 가능</li>
    </ul>
  </td>
</tr>

<tr>
 <td width="45%" align="center" valign="middle">
    <img src="https://github.com/user-attachments/assets/bd32afb1-7fa1-45af-b102-717679e0c2fc" width="180" alt="댓글 기능" />
  </td>
  <td width="55%" valign="middle">
    <h3>💬 댓글 작성 기능</h3>
    <ul>
      <li>게시글에 댓글 또는 대댓글 작성 가능</li>
      <li>비밀 댓글 설정 시 작성자와 댓글 작성자에게만 노출</li>
      <li>연락처 없이 안전한 소통 가능</li>
    </ul>
  </td>
</tr>

<tr>
 <td width="45%" align="center" valign="middle">
    <img src="https://github.com/user-attachments/assets/5d8787b9-e7d2-406d-bda5-02d681f620dc" width="180" alt="지도 기반 위치 필터링" />
  </td>
  <td width="55%" valign="middle">
    <h3>🗺 지도 기반 위치 필터링</h3>
    <ul>
      <li>캠퍼스 지도 위에 게시글 위치 표시</li>
      <li>특정 건물 클릭 시 관련 게시글 자동 필터링</li>
      <li>위치 중심 탐색으로 탐색 편의성 향상</li>
    </ul>
  </td>
</tr>

<tr>
  <td width="45%" align="center" valign="middle">
    <img src="https://github.com/user-attachments/assets/7ad3dc1c-5bc6-468e-b1b2-0a8165befa4c" width="180" alt="알림 기능" />
  </td>
  <td width="55%" valign="middle">
    <h3>🔔 알림 기능</h3>
    <ul>
      <li>내 댓글에 대댓글이 달릴 경우 알림 수신</li>
      <li>설정한 조건(종류, 위치 등)에 맞는 게시글 등록 시 자동 알림</li>
      <li>알림 기반 분실물 탐색 편의성 제공</li>
    </ul>
  </td>
</tr>
</table>

---

## 🛠 기술 스택

| 구분 | 내용 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| ORM | Spring Data JPA, Hibernate |
| DB | MySQL (AWS RDS) |
| 인증 방식 | Session (Spring Security 기반) |
| 배포 환경 | Docker, Docker Compose, Nginx (SSL 지원) |
| 도메인 | `https://ajoufinder.kr` |
| CI/CD | GitHub Actions + EC2 자동 배포 |

---
## 🏗 ER 다이어그램
![image](https://github.com/user-attachments/assets/479e6e6f-a86d-4947-8fc3-aac5774c5af3)

---
## 🏗 시스템 아키텍처
![image](https://github.com/user-attachments/assets/3a0114c8-1e38-49e1-b20d-138adec12991)

