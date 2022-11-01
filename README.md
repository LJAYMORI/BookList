# 구현내용
1. 검색 및 책정보 리스트
- 검색어 입력과 책 정보를 보여주도록 구현
- 검색어 1초이내 입력시 이전 검색 과정 취소하도록 구현
- 스크롤시 특정 영역에 도달하면 다음 페이지 호출하도록 구현
- 리스트 중 책 정보 누르면 상세 페이지로 이동하도록 구현 
- ![Screenshot_book_list](https://user-images.githubusercontent.com/12504512/199338719-80a786e9-8116-4eda-9731-d9cafacadb88.png)

2. 책 상세정보
- 리스트에서 가져온 정보를 이용해 상세정보를 보여주도록 구현
- ![Screenshot_book_detail](https://user-images.githubusercontent.com/12504512/199338740-5824e83d-c1f7-4ad2-95e5-ec047f4eeebd.png)


# 모듈구조
- ![Book List modules](https://user-images.githubusercontent.com/12504512/199342609-53f853c8-31e8-449d-9994-b0645008b006.png)
- :app - Book List를 구현하기위해 여러 feature를 조립할 수 있는 안드로이드 프로젝트 모듈
- :feature:search - 책 검색과 리스트 화면을 보여주는 화면 모듈
- :feature:detail - 책의 상세 정보를 보여주는 화면 모듈
- :core:ui - 공통적으로 사용하는 위젯과 ui를 그리기위한 데이터를 가진 모듈
- :core:domain - 공통적으로 사용하는 비지니스 로직 클래스(usecase)를 가진 모듈
- :core:util - 단순한 유틸성 기능들을 가지고 있는 모듈
- :core:data - 데이터 통신, 특히 네트워크와 db데이터를 접근할 수 있는 모듈
- :core:model - 각 모듈간 다른 형태의 데이터를 통합해주는 모듈
- :core:database - 앱 내부에 데이터를 저장 할 수 있는 모듈
- :core:network - 네트워크 통신으로 데이터를 주고 받을 수 있는 모듈


# 라이브라리
- [Retrofit2](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [Koin](https://github.com/InsertKoinIO/koin)

# 참고자료
- 모듈 구조 : [nowinandroid](https://github.com/android/nowinandroid)
- StateFlow & SharedFlow : [Android Developers](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow), [PRND 기술 블로그](https://medium.com/prnd/mvvm%EC%9D%98-viewmodel%EC%97%90%EC%84%9C-%EC%9D%B4%EB%B2%A4%ED%8A%B8%EB%A5%BC-%EC%B2%98%EB%A6%AC%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-6%EA%B0%80%EC%A7%80-31bb183a88ce)
- Fragment : [Android Developers](https://developer.android.com/guide/fragments)
