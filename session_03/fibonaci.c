#include <stdio.h>

int main()
{
    int length;
    printf("Nhap do dai fibonaci: ");
    scanf("%d", &length);

    int num1 = 1, num2 = 1, next;

    if (length > 1)
    {
        printf("%d\n", num1);
    }

    if (length > 2)
    {
        printf("%d\n", num2);
    }

    for (int i = 3; i <= length; i++)
    {
        next = num1 + num2;
        printf("%d\n", next);
        num1 = num2;
        num2 = next;
    }
}