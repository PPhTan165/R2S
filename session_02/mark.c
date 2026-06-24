#include<stdio.h>

int main() {
    int marks; 

    printf("nhap diem: ");
    scanf("%d",&marks);

    if(marks < 3){
        printf("Grade E");
    }else if(marks >= 35 && marks <45){
        printf("Grade D");
    }else if(marks >=45 && marks < 60){
        printf("Grade C");
    }else if(marks >= 60 &&  marks <75){
        printf("Grade B");
    }else if( marks >= 75){
        printf("Grade A");
    }
}