#include <stdio.h>

int main()
{
    int arr[10] = {1, 3, 3, 5, 5, 7, 7, 9, 9, 1};
    int isAllOdd =1;
    for (int i = 0; i < 10; i++)
    {
        
        if (arr[i] % 2 == 0)
        {
            isAllOdd = 0;
            break;
        }
    }

    if(isAllOdd == 1){
        printf("\nAll elements of array is odd\n");
    }else {
        printf("\nAll elements of array is not odd\n");
    }

    return 0;
}