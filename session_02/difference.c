#include<stdio.h>

int main(){
    float num1, num2;
    printf("Nhap 2 so: ");
    scanf("%f %f",&num1, &num2);
    float difference = num1 - num2; 

    printf("Hieu giua 2 so la: %f\n",difference);

    if(difference != num1 && difference != num2){
        printf("Difference is not equal to any of the values entered\n");
    }else if(difference == num1){
        printf("Difference is equal to value %f",num1);
    }else{
        printf("Difference is equal to value %f",num2);

    }
}