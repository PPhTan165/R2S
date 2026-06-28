#include <stdio.h>

#define MAX 100
#define MIN 0

void inputNums(int size, int array[])
{

    printf("\nEnter values: \n");
    for (int i = 0; i < size; i++)
    {
        printf("Element %d:  ", i + 1);
        scanf("%d", &array[i]);
    }
}

void outputNums(int size, int array[])
{   
    for (int i = 0; i < size; i++)
    {
        printf("%d ", array[i]);
    }
    printf("\n");
}

void descending_array(int size, int array[])
{
    int des_arr[size];

    for (int i = 0; i < size; i++)
    {
        des_arr[i] = array[i];
    }

    for (int i = 0; i < size - 1; i++)
    {
        for (int j = i + 1; j < size; j++)
        {
            if (des_arr[i] < des_arr[j])
            {
                int temp = des_arr[i];
                des_arr[i] = des_arr[j];
                des_arr[j] = temp;
            }
        }
    }
    outputNums(size, des_arr);
}

int checkOdd(int size, int array[])
{
    for (int i = 0; i < size; i++)
    {
        if (array[i] % 2 == 0)
        {
            return 0;
        }
    }
    return 1;
}

void findNumberInArray(int size, int array[], int number)
{
    int indexArr[size];
    int sizeIndex = 0;
    for (int i = 0; i < size; i++)
    {
        if (array[i] == number)
        {
            indexArr[sizeIndex] = i+1;
            sizeIndex++;
        }
    }
    outputNums(sizeIndex, indexArr);
}

int isPrimeNumber(int num)
{

    if (num < 2)
    {
        return 0;
    }
    for (int i = 2; i * i <= num; i++)
    {
        if (num % i == 0)
        {
            return 0;
        }
    }
    return 1;
}

void primeInArray(int size, int array[])
{
    int primeArr[size];
    int p_size = 0;

    for (int i = 0; i < size; i++)
    {
        if (isPrimeNumber(array[i]) == 1)
        {
            primeArr[p_size] = array[i];
            p_size++;
        }
    }
    if (p_size == 0)
    {
        printf("Number not found.\n");
    }
    else
    {
        outputNums(p_size, primeArr);
    }
}

int main()
{
    int choice = 0;
    int change = 0;
    int array[MAX];
    int size = MIN;
    int running = 1;

    while (running)
    {
        printf("\n====== MENU =======\n");
        printf("1. Input array\n");
        printf("2. Output array\n");
        printf("3. Descending array\n");
        printf("4. Check elements of array is odd\n");
        printf("5. Search number\n");
        printf("6. Prime numbers\n");
        printf("7. Quit\n");
        printf("Choose option: ");
        scanf("%d", &choice);

        switch (choice)
        {
        case 1:
            printf("\nEnter size array: ");
            scanf("%d", &size);
            while (size < MIN || size > MAX)
            {
                printf("\nOut of range (0-100).Please enter again: ");
                size = MIN;
                scanf("%d", size);
            }
            inputNums(size, array);
            break;
        case 2:
            printf("\nOutput Array: ");
            outputNums(size, array);
            
            break;
        case 3:
            printf("\nDescending Array: ");

            descending_array(size, array);
            break;
        case 4:
            int isOdd = checkOdd(size, array);
            if (isOdd == 1)
            {
                printf("\nElements of the array are odd");
            }
            else
            {
                printf("\nElements of the array are not odd");
            }

            break;
        case 5:
            int number;
            printf("\nEnter number: ");
            scanf("%d", &number);
            printf("\nThe index of number %d in Array is: ",number);

            findNumberInArray(size, array, number);
            break;
        case 6:
            printf("\nThe Prime number is Array: ");
            primeInArray(size, array);
            break;
        case 7:
            printf("Are you sure? Enter 1 to exit the application\n");
            scanf("%d", &change);

            if (change == 1)
            {
                printf("Quit the program.\n");
                return 0;
            }
            else
            {
                printf("Continue Program.\n");
            }
            break;
        default:
            printf("\n============Please choose option from 1-7, thank you!============\n");
            break;
        }
    }

    return 0;
}