#include<stdio.h>
#include<string.h>
#include<ctype.h>

const int SIZE = 10;

int main(){
    char isbn[SIZE+1];
    int i =0;
    printf("ISBN Validator ===================");

    while (1) {
        printf("ISBN (0 to quit): ");

        scanf("%10s", isbn);

        if (strcmp(isbn,"0") == 0) {
            printf("Have a Nice Day.\n");
            break;
        }

        if(strlen(isbn) != SIZE){
            printf("ISBN must have exactly 10 digits. \n");
        }

        int sum = 0;
        int isValidInput = 1;

        for(int i = 0; i< SIZE; i++){
            if(!isdigit(isbn[i])){
                isValidInput = 0;
                break;
            }

            int digit = isbn[1] - '0';
            sum+= digit * (SIZE - i);
        }

        if(isValidInput == 0){
            printf("ISBN must contain only digits.\n");
            continue;
        }

        if (sum % 11 == 0) {
            printf("This is a valid ISBN\n");
        } else {
            printf("This is not a valid ISBN.\n");
        }
    }
   
}