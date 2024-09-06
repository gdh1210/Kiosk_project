#include "module.h"

//.3 �޴��ۼ�
void add_menu() {
    //�޴����� üũ
    if (store_cnt >= MAX_STORE_MENU) {
        printf("�� �̻� �޴��� �߰��� �� �����ϴ�.\n");
        return;
    }
    //���� �Ҵ�
    int select1;
    //����޴��� ���� �Ǵ� ����޴� ���� ����
    printf(YELLOW"\n --�߰��� �޴� ����-- \n");
    printf(RESET"1. ����޴��߰�\n2. ����޴��߰�\n����: ");
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
            printf(YELLOW"�߸��� �����Դϴ� �ٽ� ������ �ּ���\n");
            printf(RESET"1. ����޴��߰�\n2. ����޴��߰�\n ����: ");
            scanf("%d", &select1);
            getchar();
        }
    }add_menu2();
}

void add_menu2() {
    //���� �Ҵ�
    int select2;
    //����Ʈ ȣ��� �޴��ۼ� ����
    list_up();
    do {
        // ����Ʈ ȣ��� �޴� �ۼ� ����
        printf(RESET"1. ����Ʈ ��Ȯ��\n2. �޴� �ۼ�\n");
        printf(RED"0. ���\n");
        printf(RESET"����: ");
        scanf("%d", &select2);
        getchar();
        switch (select2) {
        case 1: list_up();
            break;
        case 2: add_menu3();
            break;
        case 0: printf("�޴� �߰��� ����մϴ�.\n");
            break;
        default: printf("�߸��� �����Դϴ� �ٽ� �������ּ���.\n");
        }
    } while (select2 != 0);
}

void list_up() {
    IS menu[MAX_STORE_MENU];
    char b[256];
    //�ۼ��� ����Ʈ�� ȣ���ϴ� �Լ�
    printf(YELLOW"\n --�޴� ����Ʈ-- \n");
    FILE* fp1 = fopen(filename, "r");
    if (fp1 == NULL) {
        printf(RESET"���� �б� ����.\n");
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
    //���� ���� �߰����
    FILE* fp3 = fopen(filename, "a");
    if (fp3 == NULL) {
        printf("���� �б� ����.\n");
        return;
    }
    // ���� ������ �Է� �κ�
    printf("����: ");
    scanf("%d", &menu.num);
    getchar();
    printf("��ǰ��: ");
    fgets(menu.name, sizeof(menu.name), stdin);
    menu.name[strcspn(menu.name, "\n")] = '\0';
    printf("����: ");
    scanf("%d", &menu.price);
    fprintf(fp3, "%d\t%s\t%d\n",
        menu.num, menu.name, menu.price);
    store_cnt++;
    fclose(fp3);
}