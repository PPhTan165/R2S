#include<stdio.h>
const float PI = 3.14;

int main(){
    // tao bien ban kinh;
    float r,areas,perimeter;
    printf("\nEnter the radius of circle: ");
    scanf("%f",&r);

    areas = r*r*PI;
    perimeter = 2*r*PI;

    printf("Area of circle: %f\n",areas);
    printf("Perimeter of circle: %f\n",perimeter);
}