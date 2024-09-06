#include "module.h"

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