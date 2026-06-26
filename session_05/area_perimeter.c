#include<stdio.h>

const float PI = 3.14;

float areaCircle(float radius){
    return PI*radius*radius;
}

float perimeterCircle(float radius){
    return 2*PI*radius;
}

int main(){
    float radius;
    printf("\nEnter value radius: ");
    scanf("%2f",&radius);

    float area = areaCircle(radius);
    float perimeter = perimeterCircle(radius);

    printf("Area circle with radius %2f equal %2f \n",radius,area);
    printf("Perimeter circle with radius %2f equal %2f\n",radius,perimeter);

    
}