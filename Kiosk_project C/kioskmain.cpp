//������� �� �������� ȣ��
#include "module.h"

int order_cnt = 0;
int store_cnt = 0;
char filename[256];

int main() {
    int select = 10;
    while (select != 0) {
        printf("���� ��¥�� �ð�: %s, %s\n\n",
            __DATE__, __TIME__);
        printf(YELLOW" --�ֹ��ϱ�-- \n");
        printf(RESET"1. ����\n2. ����\n3. �޴� �ۼ�\n");
        printf(RED"0. ����\n");
        printf(RESET"����: ");
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
        case 0: printf("�ֹ��� ����մϴ�.\n");
            break;
        default: printf("�߸��� �����Դϴ�. �ٽ� �������ּ���.\n");
        }
    }
}