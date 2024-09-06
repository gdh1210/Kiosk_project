#include "module.h"

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