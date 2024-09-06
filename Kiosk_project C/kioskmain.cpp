//헤더모음 및 전역변수 호출
#include "module.h"

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