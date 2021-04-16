### 기존 프로젝트 : https://github.com/muckyang/ShallWe_fin

### 기존 프로젝트에 QueryDSL 적용 및 가독성 개선에 목표를  프로젝트 입니다.  
 

- Query DSL을 적용하여 기존 프로젝트의 문제점 해결

  - 기존 Repository의 문제점
 
    1. 길고 긴 메소드명 ex) getPostByTempAndCategoryIdNotAndStatus~~
    2. 분기에 따라 쿼리가 달라지는 쿼리를 각각 생성 
    3. 유지보수가 불가능한 페이징 처리

  - 해결방식
    
    1. JPAQueryFactroy를 이용해 가독성 높은 쿼리 코드( 컴파일 단계 디버깅, 유지보수성 향상)
    2. Condition을 이용해서 분기에 따른 처리 및 재사용성 증대
    3. Page, Slice객체를 이용, 상황에 맞는 페이징 처리 및 유지보수성 증대


  - 단점? 
    1. Q-Type 객체 관리 ? 
    2. @Builder(methodName="~~") 사용시 Querydsl 컴파일 실패 문제발생
    
    
<br/>

 - @QueryProjection을 적용, DTO 객체로 쿼리결과를 반환받음
 - @Builder의 적용으로 불변객체로 이용, 무분별한 Setter메소드 사용을 방지.
 - Request 요청시 


