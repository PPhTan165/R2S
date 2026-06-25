#include<stdio.h>

int main(){
    int arr[10] = {1,2,3,4,5,6,7,8,9,10};
            printf("The values are divisible by 3 or 5: \n");

    for(int i = 0;i < 10; i++){
        if(arr[i] % 3 == 0 || arr[i] % 5 == 0){
            printf("%d ",arr[i]);
        }
    }
}