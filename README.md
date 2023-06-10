# SimpleStockData
Investing.com이 너무 느려서 이를 보완하고자 만든 앱

## 2023-06-07 ~~ refactoring start 
https://github.com/tead1234/SimpleStockData/assets/35370337/06c2397c-4f09-46a4-968c-3d7bc7ab671e
1. 기존 허접했던 ui를 viewpager를 활용하여 보기 좋게 변경
>> compose를 활용 >> fragement에서 theme과 composable을 읽어오지 못하는 문제
2. 두번째 페이지에서 최근 관심 종목을 등록하여 확인할 수 있도록 구현
3. 10초마다 업데이트 되도록 구현하여 최대한 정확한 정보를 제공할 수 있도록 구현 ** 핵심

