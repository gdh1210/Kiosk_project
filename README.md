<div align="center">
<img src="https://capsule-render.vercel.app/api?type=venom&color=0:2080FF,100:a82da8&height=300&section=header&text=Kiosk%20project&fontColor=C8C8C8&fontSize=90&stroke=ff0084&animation=twinkling">
</div>

<div align="center">
<img src="https://img.shields.io/badge/Visual%20Studio-5C2D91.svg?style=for-the-badge&logo=visual-studio&logoColor=white">
<img src="https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white">
</div>

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
* 

# in_store.cpp

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

![image](https://github.com/user-attachments/assets/5d101903-e740-4b64-8a01-4ae48e466310)

![image](https://github.com/user-attachments/assets/5d8ab083-cbe9-4dfc-8823-43686cadb381)

![image](https://github.com/user-attachments/assets/b51019ad-6b74-4dbf-8f6a-f7b8bb7e210e)

![image](https://github.com/user-attachments/assets/371a7ee8-f3ec-4038-8bfe-62c5d9af20c4)

![image](https://github.com/user-attachments/assets/143e91e3-9bc5-41d0-8a56-c07ace3555ca)
결제 진행
![image](https://github.com/user-attachments/assets/28480e1f-9353-4808-9b92-e2c713c7dda9)




# take_out.cpp

```C++
#include "module.h"

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



# 

```java



```



### 참조
키오스크 동작 참조 - https://help-center.payhere.in/feature/kiosk/customer
