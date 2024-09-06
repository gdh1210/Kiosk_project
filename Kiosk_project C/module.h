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