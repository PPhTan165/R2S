#include<stdio.h>

int factorial(int number){
    int total =1;
    for(int i = number; i > 0;i--){
        total *= i;
    }
}

int main(){
    int number;
    printf("\nEnter value: ");
    scanf("%d",&number);

    int factorialNumber = factorial(number);
    printf("\nFactorial of number %d is %d",number,factorialNumber);
}