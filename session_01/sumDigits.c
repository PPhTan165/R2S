#include<stdio.h>

int main() {
    int num,digit1,digit2,digit3;

    printf("Nhap so co 3 chu so: \n");
    scanf("%d",&num);
    
    digit1 = num % 10;
    num /= 10;
    printf("num: %d, digit: %f\n", num, digit1);
    digit2 = num % 10;
    num /= 10;
    printf("num: %d, digit: %f\n", num, digit2);

    digit3 = num%10;
    num /= 10;
    printf("num: %d, digit: %f\n", num, digit3);
    
    int sum = digit1 + digit2 + digit3;

    printf("Tong 3 chu so la: %f",sum);
    
}