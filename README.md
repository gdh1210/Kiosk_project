<div align="center">
<img src="https://capsule-render.vercel.app/api?type=venom&color=0:2080FF,100:a82da8&height=300&section=header&text=Kiosk%20project&fontColor=C8C8C8&fontSize=90&stroke=ff0084&animation=twinkling">
</div>

<div align="center">
<img src="https://img.shields.io/badge/Visual%20Studio-5C2D91.svg?style=for-the-badge&logo=visual-studio&logoColor=white">
<img src="https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white">
</div>

## 목차

1. [C 언어를 이용한 Kiosk Project](#kioskmain)
   
2. [Java 언어를 이용한 Kiosk Project](#java-project)

---

# C코드 작성전 구성및 동작 초안

제일먼저 키오스크의 동작 구조를 살펴보고 어떤기능을 구현해야 할지 살펴보았고 정리한 자료를 바탕으로 시각화 하였다.

![image](https://github.com/user-attachments/assets/4305b8d9-af17-4403-a244-fd938ac956c2)

각각 빨간줄과 파랑줄은 데이터를 외부에 저장하고 읽어오는 방식으로 동작시 필요한 파일을 구분해놓았다.

# Module.h

```C++
#pragma once

//정의 및 scanf 함수 사용 허가작업
#define _CRT_SECURE_NO_WARNINGS
#define RED "\x1b[31m"
#define GREEN "\x1b[32m"
#define YELLOW "\x1b[33m"
#define RESET "\x1b[0m"
#define MAX_STORE_MENU 20

//헤더 파일 모음
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <windows.h>
#include <stdbool.h>

//전역변수 지정
extern int order_cnt;
extern int store_cnt;
extern char filename[256];


//제품의 항목 구조체 정보
typedef struct in_store {
	char name[30];
	int num, price;
}IS;


//함수 선언 모음
void add_menu();
void list_up();
void in_store();
void take_out();
void add_menu2();
void add_menu3();
void order_p(IS order);
void payment();
void payment2();
```

코드 상단의 라이브러리 호출 함수 모음이다. 제작시 사용된 라이브러리 호출이나 함수간 호출 연결 및 전역변수 연결, 구조체의 정보 수용을 담당하는 부분이다.  
기능적으로는 아무것도 동작하지 않으나 각 .cpp 파일의 상단에 위치하여 반복되는 호출 부분을 한줄로 압축하고 코드를 간결화 하여 가독성을 높이며 함수 호출 간에 연결로 역활로 만들었다.

# kioskmain.cpp

제일먼저 작성한 메인부분으로 어떤기능을 동작할지 선택하는 부분이다.  

```C++
int order_cnt = 0;
int store_cnt = 0;
char filename[256];

int main() {
    int select = 10;
    while (select != 0) {
        printf("현재 날짜와 시간: %s, %s\n\n",
            __DATE__, __TIME__);
        printf(YELLOW" --주문하기-- \n");
        printf(RESET"1. 매장\n2. 포장\n3. 메뉴 작성\n");
        printf(RED"0. 종료\n");
        printf(RESET"선택: ");
        scanf("%d", &select);
        getchar();
        switch (select) {
        case 1: strcpy(filename, "in_store_order.txt");
            in_store();
            break;
        case 2: strcpy(filename, "take_out_store.txt");
            take_out();
            break;
        case 3: add_menu();
            break;
        case 0: printf("주문을 취소합니다.\n");
            break;
        default: printf("잘못된 선택입니다. 다시 선택해주세요.\n");
        }
    }
}
```
메인화면을 호출하여 보이는 모습이다.  

![image](https://github.com/user-attachments/assets/2bae1794-5211-4b07-ab8f-782b763f21b9)

# add_menu.cpp

메뉴 추가를 진행하는 부분으로 위에 미리 정해둔 동작대로 매장, 포장 쪽 메뉴를 추가할지 선택하고 그에 해당하는 메뉴를 추가하면 외부 저장소에(.txt) 저장되어 다음에 호출시 그대로 사용할 수 있게 만들었다.  

```C++
//.3 메뉴작성
void add_menu() {
    //메뉴수량 체크
    if (store_cnt >= MAX_STORE_MENU) {
        printf("더 이상 메뉴를 추가할 수 없습니다.\n");
        return;
    }
    //변수 할당
    int select1;
    //매장메뉴를 수정 또는 포장메뉴 수정 선택
    printf(YELLOW"\n --추가할 메뉴 선택-- \n");
    printf(RESET"1. 매장메뉴추가\n2. 포장메뉴추가\n선택: ");
    scanf("%d", &select1);
    getchar();
    while (1) {
        if (select1 == 1) {
            strcpy(filename, "in_store_order.txt");
            break;
        }
        else if (select1 == 2) {
            strcpy(filename, "take_out_store.txt");
            break;
        }
        else {
            printf(YELLOW"잘못된 선택입니다 다시 선택해 주세요\n");
            printf(RESET"1. 매장메뉴추가\n2. 포장메뉴추가\n 선택: ");
            scanf("%d", &select1);
            getchar();
        }
    }add_menu2();
}

void add_menu2() {
    //변수 할당
    int select2;
    //리스트 호출과 메뉴작성 선택
    list_up();
    do {
        // 리스트 호출과 메뉴 작성 선택
        printf(RESET"1. 리스트 재확인\n2. 메뉴 작성\n");
        printf(RED"0. 취소\n");
        printf(RESET"선택: ");
        scanf("%d", &select2);
        getchar();
        switch (select2) {
        case 1: list_up();
            break;
        case 2: add_menu3();
            break;
        case 0: printf("메뉴 추가를 취소합니다.\n");
            break;
        default: printf("잘못된 선택입니다 다시 선택해주세요.\n");
        }
    } while (select2 != 0);
}

void list_up() {
    IS menu[MAX_STORE_MENU];
    char b[256];
    //작성된 리스트를 호출하는 함수
    printf(YELLOW"\n --메뉴 리스트-- \n");
    FILE* fp1 = fopen(filename, "r");
    if (fp1 == NULL) {
        printf(RESET"파일 읽기 실패.\n");
        return;
    }
    int i = 0;
    while (fgets(b, sizeof(b), fp1) != NULL) {
        char* t = strtok(b, "\t");
        menu[i].num = atoi(t);
        t = strtok(NULL, "\t");
        strcpy(menu[i].name, t);
        t = strtok(NULL, "\t");
        menu[i].price = atoi(t);
        printf(RESET"%-3d. %-20s %10d\n", menu[i].num, menu[i].name, menu[i].price);
        store_cnt++;
        i++;
    } fclose(fp1);
}

void add_menu3() {
    IS menu;
    //파일 오픈 추가모드
    FILE* fp3 = fopen(filename, "a");
    if (fp3 == NULL) {
        printf("파일 읽기 실패.\n");
        return;
    }
    // 내부 데이터 입력 부분
    printf("순번: ");
    scanf("%d", &menu.num);
    getchar();
    printf("제품명: ");
    fgets(menu.name, sizeof(menu.name), stdin);
    menu.name[strcspn(menu.name, "\n")] = '\0';
    printf("가격: ");
    scanf("%d", &menu.price);
    fprintf(fp3, "%d\t%s\t%d\n",
        menu.num, menu.name, menu.price);
    store_cnt++;
    fclose(fp3);
}
//줄 입력받아 삭제
void delete_line(const char* filename, int line_number) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        printf("파일 읽기 실패.\n");
    }

    FILE* temp = fopen("temp.txt", "w");
    if (temp == NULL) {
        perror("파일 쓰기 실패");
        fclose(file);
    }

    char buffer[256];
    int current_line = 1;

    while (fgets(buffer, sizeof(buffer), file) != NULL) {
        if (current_line != line_number) {
            fputs(buffer, temp);
        }
        current_line++;
    }
}
```

1을 입력해 매장메뉴를 선택했고 현재 저장된 리스트를 매장메뉴 외부저장소 txt에서 받아와 표시해준다.

![image](https://github.com/user-attachments/assets/639a175f-4891-4b12-a5ab-b5cdb3eeb6c3)

2를 입력해 메뉴작성을 순서대로 순번, 제품명, 가격 순으로 입력하여 진행하였고 1을 입력해 리스트를 수동으로 업데이트하여 정상적으로 추가되었는지 표시해준다.

![image](https://github.com/user-attachments/assets/3ad14975-c396-42c1-b321-ed34c067b705)

외부저장소인 txt파일을 열었을 때에도 정상적으로 추가가 되어있는 것을 확인 할 수 있다.

![image](https://github.com/user-attachments/assets/009a8d1d-677d-4987-aa57-b9a8ffcc0563)

시행착오 및 정리

:x:문제점
* 추가가 진행되면 삭제또한 있어야 하는데 삭제를 구현하지 않아 메뉴를 지우려면 수동으로 txt 파일을 열어 지우고 번호를 수정해야 한다.
* 파일 읽고 쓰는 단계에서 처음에 w 모드를 쓰기모드로 사용 하였는데 이 모드는 쓸때마다 txt파일 내부를 초기화 하여 내가 추가를 진행하면 기존에있던 제품들이 전부 사라지는 문제가 있었다.

:o:해결법
* 삭제에 대한 진행방식은 기존에 작성된 순번과 일치하는 줄을 찾아 삭제하고 카피본을 만든뒤 덮어쓰거나 교체하는 방식으로 진행했는데 delete_line 함수를 구현하여 줄 삭제 까지는 진행했으나 읽어오는 과정에서 중간에 빈줄을 읽어와서 오류가 발생했고 이를 해결하기 위해 구조체에 순번을 재할당하여 카피본을 만들고 기존의 파일을 삭제를 구현하는 부분 에서 어려움을 겪고 삭제버튼을 봉인해 두었다.
* 파일의 작성모드를 a로 변경하여 덮어 씌우는 방식이 아닌 추가하는 방식을 적용하여 이전 데이터가 유지되도록 하였다.

# in_store.cpp

매장 메뉴의 선택과 결제를 진행하는 부분으로 작성된 코드다.

```C++
//1.매장
void in_store() {
    //변수 지정
    IS menu[MAX_STORE_MENU];
    int order_n = 0;
    //전역변수 초기화 재로딩 대비
    int store_cnt = 0;

    // 파일 열기 in_store_menu.txt
    FILE* fp1 = fopen(filename, "r");
    if (fp1 == NULL) {
        printf(RESET"파일 읽기 실패.\n");
        return;
    }

    // 구조체 menu[i]에 데이터 읽어와 할당하기 
    char b[256];
    int i = 0;
    while (fgets(b, sizeof(b), fp1) != NULL) {
        char* t = strtok(b, "\t");
        menu[i].num = atoi(t);
        t = strtok(NULL, "\t");
        strcpy(menu[i].name, t);
        t = strtok(NULL, "\t");
        menu[i].price = atoi(t);
        i++;
        store_cnt++;
    } fclose(fp1);

    // 메뉴 선택지 제공
    printf(YELLOW"\n --메뉴를 선택하세요-- \n");
    do {
        for (int i = 0; i < store_cnt; i++) {
            printf(RESET"%-3d. %-20s %10d\n", menu[i].num, menu[i].name, menu[i].price);
        }
        printf(RED"0. 취소\n");
        printf(RESET"100 결제\n");
        printf("선택: ");
        scanf("%d", &order_n);
        getchar();

        if (order_n == 0) {
            printf("주문이 취소되었습니다.\n\n");
            break;
        } 
        else if (order_n == 100) {
            printf("결제로 넘어갑니다.\n\n");
            payment();
            order_n = 0;
            break;
        }
        else {
            bool order_v = false;
            for (int i = 0; i < MAX_STORE_MENU; i++) {
                if (menu[i].num == order_n) {
                    printf("%s를(을) 선택하셨습니다.\n\n", menu[i].name);
                    order_v = true;
                    order_p(menu[i]);
                    //옵션 자리
                    break;
                }
            } if (!order_v) printf("잘못된 선택입니다. 다시 시도하세요.\n\n");
        }
    } while (order_n != 0 && order_n != 100);
}

// 주문 기록 함수
void order_p(IS order) {
    FILE* fp_odr = fopen("order.txt", "a");
    if (fp_odr == NULL) {
        printf(RESET"파일 열기 실패.\n");
        return;
    }
    fprintf(fp_odr, "%d\t%s\t%d\n", order.num, order.name, order.price);
    fclose(fp_odr);
}

// 결제 창
void payment() {
    IS menu[MAX_STORE_MENU];
    char b[256], pay;
    int i = 0, tot_price = 0;
    FILE* fp_pay = fopen("order.txt", "r");
    if (fp_pay == NULL) {
        printf(RESET"파일 읽기 실패.\n");
        return;
    }

    printf(YELLOW"\n --결제 내역-- \n");
    while (fgets(b, sizeof(b), fp_pay) != NULL) {
        char* t = strtok(b, "\t");
        menu[i].num = atoi(t);
        t = strtok(NULL, "\t");
        strcpy(menu[i].name, t);
        t = strtok(NULL, "\t");
        menu[i].price = atoi(t);
        printf(RESET"%-3d. %-20s %10d\n", menu[i].num, menu[i].name, menu[i].price);

        tot_price += menu[i].price;
    }
    printf(YELLOW"\n 총 결제 금액: %d 원\n", tot_price);
    fclose(fp_pay);

    // 결제 여부 확인( y / n )
    do {
        printf("결제를 진행 하시겠습니까? ( y / n )\n");
        printf(RESET"선택: ");
        scanf(" %c", &pay);
        if (pay == 'y')
            payment2();
        else if (pay == 'n') {
            printf("결제를 취소 하셨습니다.\n");
            return;
        }
        else printf("다시 선택해 주세요\n");
    }while(pay != 'y' && pay != 'n');

    // 결제 후 order.txt 파일 초기화
    fp_pay = fopen("order.txt", "w");
    if (fp_pay != NULL) {
        fclose(fp_pay);
    }
}

void payment2() {
    int select;
    // 결제 방법 확인
    printf(YELLOW"\n --결제 방법을 확인해 주세요-- \n");
    printf(RESET"1. 카드 결제\n2. 간편 결제\n선택: ");
    scanf("%d", &select);
    switch (select) {
    case 1: // 카드 결제 함수 자리
        Sleep(2500);
        printf("카드 결제가 완료되었습니다.\n");
        break;
    case 2: // 간편 결제 함수 자리
        Sleep(2500);
        printf("간편 결제가 완료되었습니다.\n");
        break;
    default:
        printf("잘못된 선택입니다. 다시 시도해주세요.\n");
        payment2();
    }
}
```

매장 메뉴의 진행을 위해 메인에서 1을 입력해준후 리스트를 확인해보면 이전에 추가되었던 6번 항목이 정상적으로 추가된 것을 확인할 수 있고

![image](https://github.com/user-attachments/assets/5d101903-e740-4b64-8a01-4ae48e466310)

메뉴의 아이템을 각각 1, 3, 5를 선택하여 주문을 처리하는 order.txt에 추가되는지 확인해 보았다.

![image](https://github.com/user-attachments/assets/5d8ab083-cbe9-4dfc-8823-43686cadb381)

![image](https://github.com/user-attachments/assets/b51019ad-6b74-4dbf-8f6a-f7b8bb7e210e)

![image](https://github.com/user-attachments/assets/371a7ee8-f3ec-4038-8bfe-62c5d9af20c4)

추가된 1, 3, 5 번 항목이 정상적으로 기록되는 것을 확인 할수 있었고

![image](https://github.com/user-attachments/assets/143e91e3-9bc5-41d0-8a56-c07ace3555ca)

결제 진행시 order.txt에 저장된 내용을 성공적으로 불러온후 가격부분에서 총 합산가격이 정상적으로 표기되는 것을 확인할 수 있었다.

![image](https://github.com/user-attachments/assets/28480e1f-9353-4808-9b92-e2c713c7dda9)

결제가 완료되면 payment() 함수의 제일 밑부분에서 order.txt를 w 쓰기모드로 열어 공백을 덮어 씌움으로써 초기화를 시켜 다음 결제시 데이터의 중첩을 막는다.

시행착오 및 정리

* 이부분을 작성하면서 오류와 싸우기보다는 C언어 의 특징 및 세세한 설정 부분 에서 더 많이 시간을 잡아 먹었는데 작성하면서 포인터의 사용을 최대한 배제 하였다.
* 파일을 읽어오는 과정에서 데이터를 어떻게 처리 해야하는지 몰라서 처음에 fgets로 전부 읽게 했었는데 메모장에 작성시 tab으로 구분해 놨는데 이걸 그대로 가져와 앞에 tab을 가진채로 가져와서 strtok 으로 변경하여 구분하였다.

# take_out.cpp

이후 포장 메뉴 부분은 매장메뉴의 결제부분만 제외하고 복사 붙여넣기한 거라 약간의 수정을 하여 오래걸리지는 않았다.

```C++
//1.포장
void take_out() {
    //변수 지정
    IS menu[MAX_STORE_MENU];
    int order_n = 0;
    //전역변수 초기화 재로딩 대비
    int store_cnt = 0;

    // 파일 열기 in_store_menu.txt
    FILE* fp1 = fopen(filename, "r");
    if (fp1 == NULL) {
        printf(RESET"파일 읽기 실패.\n");
        return;
    }

    // 구조체 menu[i]에 데이터 읽어와 할당하기 
    char b[256];
    int i = 0;
    while (fgets(b, sizeof(b), fp1) != NULL) {
        char* t = strtok(b, "\t");
        menu[i].num = atoi(t);
        t = strtok(NULL, "\t");
        strcpy(menu[i].name, t);
        t = strtok(NULL, "\t");
        menu[i].price = atoi(t);
        i++;
        store_cnt++;
    } fclose(fp1);

    // 메뉴 선택지 제공
    printf(YELLOW"\n --메뉴를 선택하세요-- \n");
    do {
        for (int i = 0; i < store_cnt; i++) {
            printf(RESET"%-3d. %-20s %10d\n", menu[i].num, menu[i].name, menu[i].price);
        }
        printf(RED"0. 취소\n");
        printf(RESET GREEN"100 결제\n");
        printf(RESET"선택: ");
        scanf("%d", &order_n);
        getchar();

        if (order_n == 0) {
            printf("주문이 취소되었습니다.\n\n");
            break;
        }
        else if (order_n == 100) {
            printf("결제로 넘어갑니다.\n\n");
            payment();
            order_n = 0;
            break;
        }
        else {
            bool order_v = false;
            for (int i = 0; i < MAX_STORE_MENU; i++) {
                if (menu[i].num == order_n) {
                    printf("%s를(을) 선택하셨습니다.\n\n", menu[i].name);
                    order_v = true;
                    order_p(menu[i]);
                    break;
                }
            } if (!order_v) printf("잘못된 선택입니다. 다시 시도하세요.\n\n");
        }
    } while (order_n != 0 && order_n != 100);
}
```

매장메뉴와 이름만 다를뿐 동작은 똑같기에 따로 사진을 첨부하거나 설명은 생략 하겠다.

# 마치며
* 5일 동안 진행된 프로젝트 치고는 내용이 굉장히 적은데 C언어 특성상 무언가 기능 하나를 넣으려면 세세한 부분을 일일히 수동으로 설정해주다 보니 그런 것도 있고 중간에 포인터를 활용해 작성하다가 내가 쓴코드가 어떻게 동작하는지 이해하지 못해서 한번 싹 갈아엎은 일도 있었으며 수업시간에 따로 배우지 않은 strtok 같은 함수들을 일일히 찾아보며 문제 해결법을 찾다 보니 진행이 더뎌진 점도 있었다.
* 작성하면서 딱딱한 터미널 환경에서만 구현하여 약간 아쉬웠고 이번 프로젝트는 일일히 타이핑하여 진행하다 보니 오류의 절반 이상은 ; 과 & 의 누락 혹은 오탈자에 의해 발생 하였다.
* C언어는 접근성이 뛰어나 이후 작성된 코드를 기반으로 Java 로 코드를 옮겨 같은 프로젝트를 진행할 예정이다. (현재 교육과정상 C언어만 교육 받았음)

진행기간: 5/22(수) ~ 5/29(수) 6일 

---
#Java Project

## Kiosk.java

C언어로 구현된 키오스크 프로젝트를 참조하여 비슷한 동작방식으로 Java 에서 구현했다.  
하나의 액티비티에서 구현하다보니 길어져서 하단에 접어 놓았다.

<details>
<summary>접기/펼치기</summary>

```java
public class Kiosk extends AppCompatActivity {
    private int cnt = 0;
    private final int rowCount = 0;
    private ScrollView tab1, tab2, tab3, tab4, tab5;
    private ImageView iv1;
    private TextView optv1;
    private TextView optv2;
    private TextView tottv_cnt;
    private TextView tottv_price;
    private TextView rantv;
    private TextView payprice;
    private TextView paycount;
    private TextView paypoint;
    private TextView complete;
    private TableLayout tl;

    //main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kioskmain);
    }
    //메뉴 생성자 layout\menu\kiosk_menu.xml (수정)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.kiosk_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            setContentView(R.layout.config);
            //config 옵션의 수정
            String[] option = {"매장 메뉴 작성", "매장 메뉴 삭제", "매장 메뉴 수정","포장 메뉴 작성","포장 메뉴 삭제",
                    "포장 메뉴 수정","메인으로 돌아가기"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, option);
            ListView lv = findViewById(R.id.lv_1);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                    if (position == 0) {
                        //setContentView(R.layout.1);
                    } else if (position == 1) {
                        //setContentView(R.layout.2);
                    } else if (position == 2) {
                        //setContentView(R.layout.3);
                    } else if (position == 3) {
                        //setContentView(R.layout.4);
                    } else if (position == 4) {
                        //setContentView(R.layout.5);
                    } else if (position == 5) {
                        //setContentView(R.layout.6);
                    } else if (position == 6) {
                        setContentView(R.layout.kioskmain);
                    }
                }
            });
            return true;
        } else if (id == R.id.item2) {
            return true;
        } else if (id == R.id.item3) {
            return true;
        } else if (id == R.id.item4) {
            return true;
        } else if (id == R.id.item5) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void bt_back(View v) {setContentView(R.layout.kioskmain);}

    //store1 (in_store)
    public void bt_in(View v) {
        setContentView(R.layout.store1);
        tab1 = findViewById(R.id.sv1);
        tab2 = findViewById(R.id.sv2);
        tab3 = findViewById(R.id.sv3);
        tab4 = findViewById(R.id.sv4);
        tab5 = findViewById(R.id.sv5);
    }
    public void tabbt(View v) {
        tab1.setVisibility(View.INVISIBLE);
        tab2.setVisibility(View.INVISIBLE);
        tab3.setVisibility(View.INVISIBLE);
        tab4.setVisibility(View.INVISIBLE);
        tab5.setVisibility(View.INVISIBLE);

        if (v.getId() == R.id.tabbt_1) {
            tab1.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_2) {
            tab2.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_3) {
            tab3.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_4) {
            tab4.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.tabbt_5) {
            tab5.setVisibility(View.VISIBLE);
        }
    }
    public void stbt_2(View v) {
        setContentView(R.layout.store1);
        tab1 = findViewById(R.id.sv1);
        tab2 = findViewById(R.id.sv2);
        tab3 = findViewById(R.id.sv3);
        tab4 = findViewById(R.id.sv4);
        tab5 = findViewById(R.id.sv5);
    }
    // 이미지버튼 주문명령 처리부분(커피탭)(논커피탭)
    public void order_coffee(View v) {
        final int[] priceint = new int[1];
        final String[] selectedName = new String[1];
        final Dialog cd_option = new Dialog(this);
        cd_option.setContentView(R.layout.cd_option);
        cd_option.show();

        iv1 = cd_option.findViewById(R.id.op_iv1);
        optv1 = cd_option.findViewById(R.id.op_tv1);
        optv2 = cd_option.findViewById(R.id.op_tv2);
        tottv_cnt = cd_option.findViewById(R.id.tv_cnt);
        tottv_price = cd_option.findViewById(R.id.tv_price);


        // 버튼의 태그를 사용하여 관련 정보를 가져오기
        String tag = (String) v.getTag();
        if (tag.equals("101") || tag.equals("102")) {
            int imageId = getResources().getIdentifier("noncoffee" + tag, "drawable", getPackageName());
            int nameId = getResources().getIdentifier("noncoffee_name_" + tag, "id", getPackageName());
            int priceId = getResources().getIdentifier("noncoffee_price_" + tag, "id", getPackageName());
            iv1.setImageResource(imageId);
            selectedName[0] = ((TextView) findViewById(nameId)).getText().toString();
            String price = ((TextView) findViewById(priceId)).getText().toString();
            optv1.setText(selectedName[0]);
            optv2.setText(price);
            priceint[0] = Integer.parseInt(price.replace(",", "").replace("원", ""));
        } else {
            int imageId = getResources().getIdentifier("coffee" + tag, "drawable", getPackageName());
            int nameId = getResources().getIdentifier("coffee_name_" + tag, "id", getPackageName());
            int priceId = getResources().getIdentifier("coffee_price_" + tag, "id", getPackageName());
            iv1.setImageResource(imageId);
            selectedName[0] = ((TextView) findViewById(nameId)).getText().toString();
            String price = ((TextView) findViewById(priceId)).getText().toString();
            optv1.setText(selectedName[0]);
            optv2.setText(price);
            priceint[0] = Integer.parseInt(price.replace(",", "").replace("원", ""));
        }

        //토글 버튼 정의 부분
        ToggleButton tb1_1 = cd_option.findViewById(R.id.tb1_1);
        ToggleButton tb1_2 = cd_option.findViewById(R.id.tb1_2);
        ToggleButton tb2_1 = cd_option.findViewById(R.id.tb2_1);
        ToggleButton tb2_2 = cd_option.findViewById(R.id.tb2_2);
        ToggleButton tb2_3 = cd_option.findViewById(R.id.tb2_3);
        ToggleButton tb3_1 = cd_option.findViewById(R.id.tb3_1);
        ToggleButton tb3_2 = cd_option.findViewById(R.id.tb3_2);
        ToggleButton tb3_3 = cd_option.findViewById(R.id.tb3_3);
        ToggleButton tb3_4 = cd_option.findViewById(R.id.tb3_4);

        //토글 버튼 이벤트 처리 부분
        tb1_1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tb1_1.setAlpha(isChecked ? 1.0f : 0.5f);
            if (isChecked) {tb1_2.setChecked(false);}
        });
        tb1_2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tb1_2.setAlpha(isChecked ? 1.0f : 0.5f);
            if (isChecked) {tb1_1.setChecked(false);}
        });
        tb2_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb2_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb2_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb2_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb2_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb2_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb3_4.setOnCheckedChangeListener((buttonView, isChecked) -> tb3_4.setAlpha(isChecked ? 1.0f : 0.5f));

        //선택 완료 버튼 이벤트 처리
        Button bt1 = cd_option.findViewById(R.id.op_bt1);
        tl = findViewById(R.id.order_show);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] order_cnt = {1};
                //토글버튼 미선택시 기본값 설정
                if (!tb2_1.isChecked() && !tb2_2.isChecked() && !tb2_3.isChecked()) {
                    tb2_3.setChecked(true);
                    tb2_3.setAlpha(1.0f);
                }
                //테이블로우 생성자
                TableRow tableRow = new TableRow(Kiosk.this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                //테이블로우 내의 구성품 생성자
                TextView order_tv1 = new TextView(Kiosk.this);
                order_tv1.setText(selectedName[0]);
                order_tv1.setLayoutParams(new TableRow.LayoutParams(355, 70));
                order_tv1.setTextSize(14);

                TextView order_tv2 = new TextView(Kiosk.this);
                order_tv2.setText(String.valueOf(order_cnt[0]));
                order_tv2.setLayoutParams(new TableRow.LayoutParams(60, 70));
                order_tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                order_tv2.setTextSize(14);

                TextView order_tv3 = new TextView(Kiosk.this);
                if (tb2_1.isChecked()) priceint[0] += 500;
                if (tb2_2.isChecked()) priceint[0] += 500;
                String formattedPrice = String.format("%,d원", priceint[0] * order_cnt[0]);
                order_tv3.setText(formattedPrice);
                order_tv3.setLayoutParams(new TableRow.LayoutParams(180, 70));
                order_tv3.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                order_tv3.setTextSize(16);

                Button order_ib1 = new Button(Kiosk.this);
                order_ib1.setBackgroundResource(R.drawable.minus2);
                order_ib1.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (order_cnt[0] > 1) {
                            order_cnt[0]--;
                            cnt--;
                            order_tv2.setText(String.valueOf(order_cnt[0]));
                            updatePrice(order_tv3, priceint[0], order_cnt[0]);
                            updateTotalPrice();
                        }
                    }
                });

                Button order_ib2 = new Button(Kiosk.this);
                order_ib2.setBackgroundResource(R.drawable.plus2);
                order_ib2.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_cnt[0]++;
                        cnt++;
                        order_tv2.setText(String.valueOf(order_cnt[0]));
                        updatePrice(order_tv3, priceint[0], order_cnt[0]);
                        updateTotalPrice();
                    }
                });

                Button order_ib3 = new Button(Kiosk.this);
                order_ib3.setBackgroundResource(R.drawable.del);
                order_ib3.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tl.removeView(tableRow);
                        cnt -= order_cnt[0];
                        updateTotalPrice();
                    }
                });
                //테이블로우에 구성품 추가
                tableRow.addView(order_tv1);
                tableRow.addView(order_ib1);
                tableRow.addView(order_tv2);
                tableRow.addView(order_ib2);
                tableRow.addView(order_tv3);
                tableRow.addView(order_ib3);
                //테이블레이아웃에 테이블로우 추가
                tl.addView(tableRow);
                //기타 이벤트 처리 부분
                cnt++;
                Toast.makeText(Kiosk.this, "주문 추가 완료!", Toast.LENGTH_SHORT).show();
                cd_option.dismiss();
                tottv_cnt = findViewById(R.id.tv_cnt);
                tottv_price = findViewById(R.id.tv_price);
                tottv_cnt.setText("총 " + cnt + "개 결제");
                updateTotalPrice();
            }
        });
    }
    // 이미지버튼 주문명령 처리부분(스무디탭)
    public void order_smoothie(View v) {
        final int[] priceint = new int[1];
        final String[] selectedName = new String[1];
        final Dialog cd_option2 = new Dialog(this);
        cd_option2.setContentView(R.layout.cd_option2);
        cd_option2.show();

        iv1 = cd_option2.findViewById(R.id.op_iv21);
        optv1 = cd_option2.findViewById(R.id.op_tv21);
        optv2 = cd_option2.findViewById(R.id.op_tv22);
        tottv_cnt = cd_option2.findViewById(R.id.tv_cnt);
        tottv_price = cd_option2.findViewById(R.id.tv_price);

        // 버튼의 태그를 사용하여 관련 정보를 가져오기
        String tag = (String) v.getTag();
        int imageId = getResources().getIdentifier("smoothie" + tag, "drawable", getPackageName());
        int nameId = getResources().getIdentifier("smoothie_name_" + tag, "id", getPackageName());
        int priceId = getResources().getIdentifier("smoothie_price_" + tag, "id", getPackageName());
        iv1.setImageResource(imageId);
        selectedName[0] = ((TextView) findViewById(nameId)).getText().toString();
        String price = ((TextView) findViewById(priceId)).getText().toString();
        optv1.setText(selectedName[0]);
        optv2.setText(price);
        priceint[0] = Integer.parseInt(price.replace(",", "").replace("원", ""));

        //토글 버튼 정의 부분
        ToggleButton tb21_1 = cd_option2.findViewById(R.id.tb21_2);
        ToggleButton tb22_1 = cd_option2.findViewById(R.id.tb22_1);
        ToggleButton tb22_2 = cd_option2.findViewById(R.id.tb22_2);
        ToggleButton tb22_3 = cd_option2.findViewById(R.id.tb22_3);
        ToggleButton tb23_1 = cd_option2.findViewById(R.id.tb23_1);
        ToggleButton tb23_2 = cd_option2.findViewById(R.id.tb23_2);
        ToggleButton tb23_3 = cd_option2.findViewById(R.id.tb23_3);
        ToggleButton tb23_4 = cd_option2.findViewById(R.id.tb23_4);

        //토글 버튼 이벤트 처리 부분
        tb21_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb21_1.setAlpha(1.0f));
        tb22_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb22_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb22_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb22_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb22_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb22_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_1.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_1.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_2.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_2.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_3.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_3.setAlpha(isChecked ? 1.0f : 0.5f));
        tb23_4.setOnCheckedChangeListener((buttonView, isChecked) -> tb23_4.setAlpha(isChecked ? 1.0f : 0.5f));

        Button bt1 = cd_option2.findViewById(R.id.op_bt21);
        tl = findViewById(R.id.order_show);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] order_cnt = {1};
                //토글버튼 미선택시 기본값 설정
                if (!tb22_1.isChecked() && !tb22_2.isChecked() && !tb22_3.isChecked()) {
                    tb22_3.setChecked(true);
                    tb22_3.setAlpha(1.0f);
                }
                //테이블로우 생성자
                TableRow tableRow = new TableRow(Kiosk.this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                //테이블로우 내의 구성품 생성자
                TextView order_tv21 = new TextView(Kiosk.this);
                order_tv21.setText(selectedName[0]);
                order_tv21.setLayoutParams(new TableRow.LayoutParams(355, 70));
                order_tv21.setTextSize(14);

                TextView order_tv22 = new TextView(Kiosk.this);
                order_tv22.setText(String.valueOf(order_cnt[0]));
                order_tv22.setLayoutParams(new TableRow.LayoutParams(60, 70));
                order_tv22.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                order_tv22.setTextSize(14);

                TextView order_tv23 = new TextView(Kiosk.this);
                if (tb22_1.isChecked()) priceint[0] += 500;
                if (tb22_2.isChecked()) priceint[0] += 700;
                String formattedPrice = String.format("%,d원", priceint[0] * order_cnt[0]);
                order_tv23.setText(formattedPrice);
                order_tv23.setLayoutParams(new TableRow.LayoutParams(180, 70));
                order_tv23.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                order_tv23.setTextSize(16);

                Button order_ib21 = new Button(Kiosk.this);
                order_ib21.setBackgroundResource(R.drawable.minus2);
                order_ib21.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (order_cnt[0] > 1) {
                            order_cnt[0]--;
                            cnt--;
                            order_tv22.setText(String.valueOf(order_cnt[0]));
                            updatePrice(order_tv23, priceint[0], order_cnt[0]);
                            updateTotalPrice();
                        }
                    }
                });
                Button order_ib22 = new Button(Kiosk.this);
                order_ib22.setBackgroundResource(R.drawable.plus2);
                order_ib22.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order_cnt[0]++;
                        cnt++;
                        order_tv22.setText(String.valueOf(order_cnt[0]));
                        updatePrice(order_tv23, priceint[0], order_cnt[0]);
                        updateTotalPrice();
                    }
                });

                Button order_ib23 = new Button(Kiosk.this);
                order_ib23.setBackgroundResource(R.drawable.del);
                order_ib23.setLayoutParams(new TableRow.LayoutParams(60, 60));
                order_ib23.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tl.removeView(tableRow);
                        cnt -= order_cnt[0];
                        updateTotalPrice();
                    }
                });
                //테이블로우에 구성품 추가
                tableRow.addView(order_tv21);
                tableRow.addView(order_ib21);
                tableRow.addView(order_tv22);
                tableRow.addView(order_ib22);
                tableRow.addView(order_tv23);
                tableRow.addView(order_ib23);
                //테이블레이아웃에 테이블로우 추가
                tl.addView(tableRow);
                //기타 이벤트 처리 부분
                cnt++;
                Toast.makeText(Kiosk.this, "주문 추가 완료!", Toast.LENGTH_SHORT).show();
                cd_option2.dismiss();
                tottv_cnt = findViewById(R.id.tv_cnt);
                tottv_price = findViewById(R.id.tv_price);
                tottv_cnt.setText("총 " + cnt + "개 결제");
                updateTotalPrice();
            }
        });
    }
    // 이미지버튼 주문명령 처리부분(디저트탭)
    public void order_dessert(View v) {
        final int[] order_cnt = {1};

        tottv_cnt = findViewById(R.id.tv_cnt);
        tottv_price = findViewById(R.id.tv_price);

        // 버튼의 태그를 사용하여 관련 정보를 가져오기
        String tag = (String) v.getTag();
        int nameId = getResources().getIdentifier("dessert_name_" + tag, "id", getPackageName());
        int priceId = getResources().getIdentifier("dessert_price_" + tag, "id", getPackageName());
        String name = ((TextView) findViewById(nameId)).getText().toString();
        String price = ((TextView) findViewById(priceId)).getText().toString();
        int priceint = Integer.parseInt(price.replace(",", "").replace("원", ""));

        tl = findViewById(R.id.order_show);
        //테이블로우 생성자
        TableRow tableRow = new TableRow(Kiosk.this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        //테이블로우 내의 구성품 생성자
        TextView order_tv31 = new TextView(Kiosk.this);
        order_tv31.setText(name);
        order_tv31.setLayoutParams(new TableRow.LayoutParams(355, 70));
        order_tv31.setTextSize(14);

        TextView order_tv32 = new TextView(Kiosk.this);
        order_tv32.setText(String.valueOf(order_cnt[0]));
        order_tv32.setLayoutParams(new TableRow.LayoutParams(60, 70));
        order_tv32.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        order_tv32.setTextSize(14);

        TextView order_tv33 = new TextView(Kiosk.this);
        String formattedPrice = String.format("%,d원", priceint * order_cnt[0]);
        order_tv33.setText(formattedPrice);
        order_tv33.setLayoutParams(new TableRow.LayoutParams(180, 70));
        order_tv33.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        order_tv33.setTextSize(16);

        Button order_ib31 = new Button(Kiosk.this);
        order_ib31.setBackgroundResource(R.drawable.minus2);
        order_ib31.setLayoutParams(new TableRow.LayoutParams(60, 60));
        order_ib31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order_cnt[0] > 1) {
                    order_cnt[0]--;
                    cnt--;
                    order_tv32.setText(String.valueOf(order_cnt[0]));
                    updatePrice(order_tv33, priceint, order_cnt[0]);
                    updateTotalPrice();
                }
            }
        });
        Button order_ib32 = new Button(Kiosk.this);
        order_ib32.setBackgroundResource(R.drawable.plus2);
        order_ib32.setLayoutParams(new TableRow.LayoutParams(60, 60));
        order_ib32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_cnt[0]++;
                cnt++;
                order_tv32.setText(String.valueOf(order_cnt[0]));
                updatePrice(order_tv33, priceint, order_cnt[0]);
                updateTotalPrice();
            }
        });

        Button order_ib33 = new Button(Kiosk.this);
        order_ib33.setBackgroundResource(R.drawable.del);
        order_ib33.setLayoutParams(new TableRow.LayoutParams(60, 60));
        order_ib33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tl.removeView(tableRow);
                cnt -= order_cnt[0];
                updateTotalPrice();
            }
        });

        //테이블로우에 구성품 추가
        tableRow.addView(order_tv31);
        tableRow.addView(order_ib31);
        tableRow.addView(order_tv32);
        tableRow.addView(order_ib32);
        tableRow.addView(order_tv33);
        tableRow.addView(order_ib33);
        //테이블레이아웃에 테이블로우 추가
        tl.addView(tableRow);

        //기타 이벤트 처리 부분
        cnt++;
        Toast.makeText(Kiosk.this, "주문 추가 완료!", Toast.LENGTH_SHORT).show();
        tottv_cnt.setText("총 " + cnt + "개 결제");
        updateTotalPrice();
    }
    public void ib_cancel(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("주문 내역 전체 삭제");
        builder.setMessage("주문을 취소 하시겠습니까?");
        builder.setNegativeButton("취소", null);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tl.removeAllViews();
                cnt = 0;
                updateTotalPrice();
                tottv_cnt.setText("총 " + cnt + "개 결제");
                Toast.makeText(Kiosk.this, "주문 취소 완료!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updatePrice(TextView order_tv3, int pricePerItem, int itemCount) {
        int totalPrice = pricePerItem * itemCount;
        String formattedPrice = String.format("%,d원", totalPrice);
        order_tv3.setText(formattedPrice);
    }
    private void updateTotalPrice() {
        int totalOrderPrice = 0;
        for (int i = 0; i < tl.getChildCount(); i++) {
            TableRow row = (TableRow) tl.getChildAt(i);
            TextView priceView = (TextView) row.getChildAt(4);
            String priceText = priceView.getText().toString().replace(",", "").replace("원", "");
            totalOrderPrice += Integer.parseInt(priceText);
        }
        String formattedTotal = String.format("%,d원", totalOrderPrice);
        tottv_price.setText(formattedTotal);
        tottv_cnt.setText("총 " + cnt + "개 결제");
    }
    public void ib_pay(View v) {
        if (cnt == 0) {
            Toast.makeText(Kiosk.this, "주문이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {setContentView(R.layout.payment);}
        // TableLayout에서 데이터 추출
        List<data> list = new ArrayList<>();
        for (int i = 0; i < tl.getChildCount(); i++) {
            TableRow row = (TableRow) tl.getChildAt(i);
            TextView nameView = (TextView) row.getChildAt(0);
            TextView countView = (TextView) row.getChildAt(2);
            TextView priceView = (TextView) row.getChildAt(4);
            String name = nameView.getText().toString();
            String count = countView.getText().toString();
            String price = priceView.getText().toString();
            data data = new data(name, count, price);
            list.add(data);
        }
        // ListView에 데이터 설정
        ListView lv_pay = findViewById(R.id.lv_pay);
        ArrayAdapter<data> adapter = new ArrayAdapter<data>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                list
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                data currentData = getItem(position);
                text1.setText(currentData.getName());
                text2.setText(currentData.getCount() + "개 - " + currentData.getPrice());

                return view;
            }
        };
        lv_pay.setAdapter(adapter);

        paycount = findViewById(R.id.payment_tv2);
        payprice = findViewById(R.id.payment_tv3);
        paypoint = findViewById(R.id.tv_point);
        paycount.setText(cnt + " 개");
        payprice.setText(tottv_price.getText().toString());
        int point = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
        int a = (int) (point * 0.1);
        paypoint.setText(String.format("%s P", a));
    }

    public class data {
        String name;
        String count;
        String price;

        public data(String name, String count, String price) {
            this.name = name;
            this.count = count;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getCount() {
            return count;
        }

        public String getPrice() {
            return price;
        }
    }

    //store2 (take_out)
    public void bt_out(View v) {
        setContentView(R.layout.store2);
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Drinks").setIndicator("커피");
        tabSpec.setContent(R.id.coffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("논커피");
        tabSpec.setContent(R.id.noncoffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("스무디");
        tabSpec.setContent(R.id.smoothie);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("주스");
        tabSpec.setContent(R.id.juice);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("디저트");
        tabSpec.setContent(R.id.dessert);
        tabHost.addTab(tabSpec);
    }
    public void stbt_1(View v) {
        setContentView(R.layout.store2);
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Drinks").setIndicator("커피");
        tabSpec.setContent(R.id.coffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("논커피");
        tabSpec.setContent(R.id.noncoffee);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("스무디");
        tabSpec.setContent(R.id.smoothie);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("주스");
        tabSpec.setContent(R.id.juice);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("Drinks").setIndicator("디저트");
        tabSpec.setContent(R.id.dessert);
        tabHost.addTab(tabSpec);
    }
    //결제 창 작성
    public void bt_payment(View v) {
        final Dialog coupon = new Dialog(this);
        coupon.setContentView(R.layout.custom2);
        coupon.show();
        Button bt_coupon1 = coupon.findViewById(R.id.bt_payment_1);
        bt_coupon1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et_coupon = coupon.findViewById(R.id.et_payment_1);
                payprice = findViewById(R.id.payment_tv3);
                String coupon_num = et_coupon.getText().toString();
                int price = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
                int discountedPrice = price;

                switch (coupon_num) {
                    case "1111":
                        if (price > 5000) {
                            discountedPrice = price - 5000;
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 5000원 할인 적용.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Kiosk.this, "주문 가격이 5000원 이상이어야 쿠폰 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case "2222":
                        if (price > 10000) {
                            discountedPrice = price - 10000;
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 10000원 할인 적용.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Kiosk.this, "주문 가격이 10000원 이상이어야 쿠폰 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case "3333":
                        if (price > 20000) {
                            discountedPrice = price - 20000;
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 20000원 할인 적용.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Kiosk.this, "주문 가격이 20000원 이상이어야 쿠폰 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case "4444":
                        if (price > 0) {
                            discountedPrice = (int) Math.round(price * 0.8);
                            Toast.makeText(Kiosk.this, "쿠폰 번호 등록 완료! 20% 할인 적용.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(Kiosk.this, "쿠폰 번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        return;
                }

                coupon.dismiss();
                payprice.setText(String.format("%,d원", discountedPrice));
            }
        });
        Button bt_coupon2 = coupon.findViewById(R.id.bt_payment_2);
        bt_coupon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupon.dismiss();
            }
        });
    }
    public void paycard_bt(View v) {
        final Dialog paycard = new Dialog(this);
        paycard.setContentView(R.layout.payment_card);
        TextView dialogpay = paycard.findViewById(R.id.tv_card2);
        dialogpay.setText(payprice.getText().toString());
        paycard.show();
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        paycard.dismiss();
                        setContentView(R.layout.paycomplete);
                        rantv = findViewById(R.id.tvpaycom_1);
                        complete = findViewById(R.id.tvpaycom_2);
                        if (rantv != null) {
                            Random random = new Random();
                            int rannum = random.nextInt(100) + 1;
                            rantv.setText("# " + rannum);
                        }
                        int point = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
                        int a = (int) (point * 0.1);
                        complete.setText(String.format("%s P", a));
                    }
                });
            }
        }, 3000);
    }
    public void payeasy_bt(View v) {
        final Dialog payeasy = new Dialog(this);
        payeasy.setContentView(R.layout.payment_easy);
        TextView dialogpay = payeasy.findViewById(R.id.tv_easy2);
        dialogpay.setText(payprice.getText().toString());
        payeasy.show();
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        payeasy.dismiss();
                        setContentView(R.layout.paycomplete);
                        rantv = findViewById(R.id.tvpaycom_1);
                        complete = findViewById(R.id.tvpaycom_2);
                        if (rantv != null) {
                            Random random = new Random();
                            int rannum = random.nextInt(100) + 1;
                            rantv.setText("# " + rannum);
                        }
                        int point = Integer.parseInt(tottv_price.getText().toString().replace(",", "").replace("원", ""));
                        int a = (int) (point * 0.1);
                        complete.setText(String.format("%s P", a));
                    }
                }); 
            }
        }, 3000);
    }
    public void pay_bt1(View v) {
        cnt = 0;
        setContentView(R.layout.store1);
        tab1 = findViewById(R.id.sv1);
        tab2 = findViewById(R.id.sv2);
        tab3 = findViewById(R.id.sv3);
        tab4 = findViewById(R.id.sv4);
        tab5 = findViewById(R.id.sv5);
    }
    //결제 완료 후 창
    public void btpaycom(View v) {
        cnt = 0;
        setContentView(R.layout.kioskmain);
    }
    public void btpaycom2(View v) {
        Toast.makeText(Kiosk.this, "영수증 출력 완료!", Toast.LENGTH_SHORT).show();
    }
    //config
}
```
</details>

각 동작별 화면 설명

* 시작시 메인 화면으로 메뉴와 포장을 선택할 수 있다.  
* 메뉴선택시 보이는 초기 화면이며 탭의기능을 하는 버튼 제품을 구성하는 이미지 버튼 제품의 입력정보를 기록하는 테이블레이아웃 으로 구성되어 있다.  

<div align="center">
<img src="https://github.com/user-attachments/assets/366fde64-891e-466a-bb2e-d779f0f52450" width="300" height="600">
<img src="https://github.com/user-attachments/assets/fc0f3197-23f7-4594-8e7a-8d96191d771d" width="300" height="600">
</div>

* 메뉴화면에서 제품을 선택시 보이는 화면으로 스무디와 구성이 다르고 디저트는 선택시 바로 기록이 가능하도록 설정되어있다.  
* 주문목록에 추가된 제품은 +, - 버튼을 통해 수량을 조정할수 있고 x 버튼을 통해 선택적 삭제 또한 가능하다 그리고 우측의 개수와 가격부분을 업데이트 하도록 설정했다.
* 전체삭제 버튼을 누를시 기록되어있는 메뉴 전체를 삭제한다.

<div align="center">
<img src="https://github.com/user-attachments/assets/99c4b110-71ed-4d2c-9744-b0c5887fd702" width="260" height="550">
<img src="https://github.com/user-attachments/assets/c393b4f8-189f-4d9e-bee3-938e8d4e8933" width="260" height="550">
<img src="https://github.com/user-attachments/assets/6deac00e-65a8-4139-bf6e-c8725d1a2fbd" width="260" height="550">
</div>

* 메뉴선택이 완료된 이후 결제창으로 넘어오면 보이는 화면이다 상단의 리스트에 주문했던 내역들이 나오며 하단의 블록에 개수와 가격을 표시한다.
* 쿠폰 사용하기 버튼을 통해 특정 번호를 입력 받으면 가격을 할인하는 방식으로 구현해 놓았고 총 가격부분을 조정하여 업데이트 하는 방식으로 진행된다.
> 1111: 5000원 할인
> 2222: 10000원 할인

<div align="center">
<img src="https://github.com/user-attachments/assets/559feb62-f01e-4b22-8f6d-1438e4441f0c" width="260" height="550">
<img src="https://github.com/user-attachments/assets/1933b29d-6c81-4690-a20d-9ebb7e3a106d" width="260" height="550">
<img src="https://github.com/user-attachments/assets/9279932e-1bc8-4ce6-8b2e-57f1780d5ca3" width="260" height="550">
</div>

* 카드결제를 선택시 나오는 화면으로 카드 투입과 결제하는 부분을 딜레이를 통해 구현했고 2초뒤 다음 화면으로 넘어간다.
* 결제가 끝이나고 제품을 받아갈 주문번호, 영수증 출력 유무 그리고 초기 화면으로 돌아가기 까지 구성되어있다.

<div align="center">
<img src="https://github.com/user-attachments/assets/4da531e9-e899-4c70-bac7-a19c6da04e01" width="300" height="600">
<img src="https://github.com/user-attachments/assets/e5814922-902d-4086-85c0-65e87014c67c" width="300" height="600">
</div>

초기화면인 메인으로 돌아오면 위의 동작들을 다시 수행 가능하다.

# 마치며

* 작성을 하다보니 하나의 액티비티에서 진행을 하게 되었는데 오전에는 수업을 진행하고 오후에는 프로젝트작업을 병행하다보니 프로젝트 작업 시간이 부족하기도 했고 작성 당시 안드로이드 환경에 익숙하지 않고 GUI 위주로 교육을 진행하다 보니 액티비티 분리하는 개념을 잘몰라서 그런면도 있었다.
* 기존에 C언어로 만들었던 기능을 그대로 Java에서 구현 하였는데 GUI의 유무도 그렇고 완전히 다른 언어를 사용하다보니 구상이나 동작 방식은 참조가 가능했는데 코드짤 때에는 그냥 프로젝트를 하나 더 진행하는 느낌이었고 각 동작을 구현하는데 C언어 보다 수월하다는 느낌을 받았다.
* 포장 부분은 복붙으로 똑같이 만드려고 했으나 onclick 이벤트 설정부분이나. 액티비티 내부에 id를 참조하는 부분들이 생각 보다 많아서 시간이 많이 들어갈 것 같아 구현을 포기했다 또한 사용자 정보를 화이트 리스트의 형식으로 저장해 기록된 포인트를 사용하여 할인 받는 방식을 구현 하려고 했으나 프로젝트의 진행 시간 부족으로 구상만 하고 구현은 하지 못했다.

진행기간: 7/29(월) ~ 8/2(금) 5일

### 참조
키오스크 동작 참조 - https://help-center.payhere.in/feature/kiosk/customer  
키오스크 동작 참조2 - https://www.hani.co.kr/arti/economy/economy_general/1021163.html  
Java tablelayout 활용 - https://stackoverflow.com/questions/6976971/index-of-table-row-in-tablelayout  
안드로이드 tablelayout 활용 - https://ju-hy.tistory.com/54  
안드로이드 Kotlin 언어 - https://appmaster.io/ko/blog/kotlin-gaebalja-doegi  
안드로이드 탭버튼 구현및 이미지 삽입 - https://stackoverflow.com/questions/17648780/how-to-place-image-in-a-tab-so-that-it-looks-like-an-icon  
Kotlin 언어 사용방법 소개 - https://kotlinlang.org/docs/getting-started.html  
chat gpt 안드로이드 구성 관련 질문 - https://chatgpt.com/share/a354d3c5-47ba-41c2-9668-6c688c3ebc4b  
안드로이드 UI 조사 - https://kr.pinterest.com/citysite1025/android-ui/  
