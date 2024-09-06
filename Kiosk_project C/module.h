#pragma once

//���� �� scanf �Լ� ��� �㰡�۾�
#define _CRT_SECURE_NO_WARNINGS
#define RED "\x1b[31m"
#define GREEN "\x1b[32m"
#define YELLOW "\x1b[33m"
#define RESET "\x1b[0m"
#define MAX_STORE_MENU 20

//��� ���� ����
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <windows.h>
#include <stdbool.h>

//�������� ����
extern int order_cnt;
extern int store_cnt;
extern char filename[256];


//��ǰ�� �׸� ����ü ����
typedef struct in_store {
	char name[30];
	int num, price;
}IS;


//�Լ� ���� ����
void add_menu();
void list_up();
void in_store();
void take_out();
void add_menu2();
void add_menu3();
void order_p(IS order);
void payment();
void payment2();