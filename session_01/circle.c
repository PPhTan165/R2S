#include<stdio.h>
const float PI = 3.14;

int main(){
    // tao bien ban kinh;
    float r,areas,perimeter;
    printf("Nhap ban kinh hinh tron: ");
    scanf("%f",&r);

    areas = r*r*PI;
    perimeter = 2*r*PI;

    printf("chu vi hinh tron: %f\n",areas);
    printf("dien tich hinh tron: %f\n",perimeter);
}