#include<stdio.h>

int main(){

    int arr[10] = {-1,-4,5,-2,1,3,6,7,8,0};

    printf("The values less than 0 are: \n");

    for(int i =0;i<10;i++){
        if(arr[i] < 0){
            printf("%d ",arr[i]);
        }
    }
}