#include<stdio.h>
#include<math.h>

int main(){
    float a,b,c;

    printf("Nhap do dai 3 canh: ");
    scanf("%f %f %f",&a,&b,&c);

    float s = (a+b+c) /2;

    float areas = sqrt(s*(s-a) * (s-b) *(s-c));

    printf("Dien tich hinh tam giac la: %f",areas);
}