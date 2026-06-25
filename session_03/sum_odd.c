#include <stdio.h>

int main()
{
    int num1, num2;

    printf("Nhap 2 so: ");
    scanf("%d %d", &num1, &num2);

    int start = num1 < num2 ? num1 : num2;
    int end = num1 > num2 ? num1 : num2;

    int sum = 0;
    for (int i = start + 1; i < end; i++)
    {
        if (i % 2 != 0)
        {
            sum += i;
            printf("%d\n", i);
        }
    }
    printf("Tong cac so le giua 2 so la: %d", sum);
}