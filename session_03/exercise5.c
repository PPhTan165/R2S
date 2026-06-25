#include<stdio.h>

int main(){
    
    for(int k = 7; k >= 1 ; k--){
        for(int l = 1; l <=k;l++ ){
            printf("*");
        }
        printf("\n");
    }
}