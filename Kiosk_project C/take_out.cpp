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