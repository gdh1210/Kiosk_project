#include "module.h"

//1.����
void take_out() {
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
        printf(RESET GREEN"100 ����\n");
        printf(RESET"����: ");
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
                    break;
                }
            } if (!order_v) printf("�߸��� �����Դϴ�. �ٽ� �õ��ϼ���.\n\n");
        }
    } while (order_n != 0 && order_n != 100);
}