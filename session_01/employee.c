#include<stdio.h>

int main(){
    //can 2 bien salary va age
    int age;
    float salary;

    //nhap 2 bien tuoi va luong
    printf("Nhap tuoi va luong nhan vien: ");
    scanf("%d %f",&age, &salary);

    //in ra tuoi va luong
    printf("Tuoi: %d, Luong: %lf",age, salary);

}