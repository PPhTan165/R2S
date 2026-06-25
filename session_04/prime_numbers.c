#include <stdio.h>
#include <math.h>
int main()
{
    int arr[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11};

    for (int i = 0; i < 10; i++){
        int isPrime = 1;

        if (arr[i] < 2){
            isPrime = 0;

        } else{
            for (int j = 2; j * j <= arr[i]; j++){
                if (arr[i] % j == 0)
                {
                    isPrime = 0;
                    break;
                }
            }
        }

        if (isPrime == 1){
            printf("%d ", arr[i]);
        }
    }

    return 0;
}