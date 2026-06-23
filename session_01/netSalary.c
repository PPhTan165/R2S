#include<stdio.h>
const float BASIC_SALARY = 12000;

int main(){
    int hra = 150,ta = 120,others = 450;
    float da = BASIC_SALARY * (12/100);
    float pf = BASIC_SALARY * (14/100);
    float it = BASIC_SALARY * (15/100);
    float netSalary = BASIC_SALARY + da + hra + ta + others - (pf - it);

    printf("Luong net cua nhan vien la: %f",netSalary);

}