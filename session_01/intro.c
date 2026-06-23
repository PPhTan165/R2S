#include<stdio.h>

int main(){
    int myAge;
    char myGender;

    printf("Type a number and a character and press enter: \n");

    scanf("%d %c",&myAge,&myGender);

    printf("Your age is: %d\n",myAge);
    printf("Your gender is: %c\n",myGender);
}