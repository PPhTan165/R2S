#include <stdio.h>

int main()
{
    int num1, num2;

    printf("Nhap 2 so: ");
    scanf("%d %d", &num1, &num2);

    int multi = num1 * num2;
    if (multi >= 1000)
    {
        printf("Tich 2 so lon hon 1000");
    }
    else
    {
        printf("Tich 2 so nho hon 1000");
    }
}