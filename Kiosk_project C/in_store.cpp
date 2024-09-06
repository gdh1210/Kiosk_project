#include "module.h"

//1.����
void in_store() {
    //���� ����
    IS menu[MAX_STORE_MENU];
    int order_n = 0;
    //�������� �ʱ�ȭ ��ε� ���
    int store_cnt = 0;

    // ���� ���� in_store_menu.txt
    FILE* fp1 = fopen(filename, "r");
    if (fp1 == NULL) {
        printf(RESET"���� �б� ����.\n");
        return;
    }

    // ����ü menu[i]�� ������ �о�� �Ҵ��ϱ� 
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

    // �޴� ������ ����
    printf(YELLOW"\n --�޴��� �����ϼ���-- \n");
    do {
        for (int i = 0; i < store_cnt; i++) {
            printf(RESET"%-3d. %-20s %10d\n", menu[i].num, menu[i].name, menu[i].price);
        }
        printf(RED"0. ���\n");
        printf(RESET"100 ����\n");
        printf("����: ");
        scanf("%d", &order_n);
        getchar();

        if (order_n == 0) {
            printf("�ֹ��� ��ҵǾ����ϴ�.\n\n");
            break;
        } 
        else if (order_n == 100) {
            printf("������ �Ѿ�ϴ�.\n\n");
            payment();
            order_n = 0;
            break;
        }
        else {
            bool order_v = false;
            for (int i = 0; i < MAX_STORE_MENU; i++) {
                if (menu[i].num == order_n) {
                    printf("%s��(��) �����ϼ̽��ϴ�.\n\n", menu[i].name);
                    order_v = true;
                    order_p(menu[i]);
                    //�ɼ� �ڸ�
                    break;
                }
            } if (!order_v) printf("�߸��� �����Դϴ�. �ٽ� �õ��ϼ���.\n\n");
        }
    } while (order_n != 0 && order_n != 100);
}

// �ֹ� ��� �Լ�
void order_p(IS order) {
    FILE* fp_odr = fopen("order.txt", "a");
    if (fp_odr == NULL) {
        printf(RESET"���� ���� ����.\n");
        return;
    }
    fprintf(fp_odr, "%d\t%s\t%d\n", order.num, order.name, order.price);
    fclose(fp_odr);
}

// ���� â
void payment() {
    IS menu[MAX_STORE_MENU];
    char b[256], pay;
    int i = 0, tot_price = 0;
    FILE* fp_pay = fopen("order.txt", "r");
    if (fp_pay == NULL) {
        printf(RESET"���� �б� ����.\n");
        return;
    }

    printf(YELLOW"\n --���� ����-- \n");
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
    printf(YELLOW"\n �� ���� �ݾ�: %d ��\n", tot_price);
    fclose(fp_pay);

    // ���� ���� Ȯ��( y / n )
    do {
        printf("������ ���� �Ͻðڽ��ϱ�? ( y / n )\n");
        printf(RESET"����: ");
        scanf(" %c", &pay);
        if (pay == 'y')
            payment2();
        else if (pay == 'n') {
            printf("������ ��� �ϼ̽��ϴ�.\n");
            return;
        }
        else printf("�ٽ� ������ �ּ���\n");
    }while(pay != 'y' && pay != 'n');

    // ���� �� order.txt ���� �ʱ�ȭ
    fp_pay = fopen("order.txt", "w");
    if (fp_pay != NULL) {
        fclose(fp_pay);
    }
}

void payment2() {
    int select;
    // ���� ��� Ȯ��
    printf(YELLOW"\n --���� ����� Ȯ���� �ּ���-- \n");
    printf(RESET"1. ī�� ����\n2. ���� ����\n����: ");
    scanf("%d", &select);
    switch (select) {
    case 1: // ī�� ���� �Լ� �ڸ�
        Sleep(2500);
        printf("ī�� ������ �Ϸ�Ǿ����ϴ�.\n");
        break;
    case 2: // ���� ���� �Լ� �ڸ�
        Sleep(2500);
        printf("���� ������ �Ϸ�Ǿ����ϴ�.\n");
        break;
    default:
        printf("�߸��� �����Դϴ�. �ٽ� �õ����ּ���.\n");
        payment2();
    }
}