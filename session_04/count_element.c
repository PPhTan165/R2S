#include<stdio.h>

int main(){
    int arr[10] = {1,1,3,2,2,2,7,8,0,6};
    int value,count = 0;

    printf("Enter value count: ");
    scanf("%d",&value);

    for(int i = 0; i< 10; i++){
        if(arr[i] == value){
            count++;
        }
    }
    printf("\nThe value of %d was found %d times",value, count);
}