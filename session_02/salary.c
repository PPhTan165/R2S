#include <stdio.h>

int main()
{
    char grade;
    int salary;

    printf("Nhap salary va grade cho nhan vien: ");
    scanf("%c %d", &grade, &salary);
    int finalSalary = salary;
    if (grade != 'A' && grade != 'B')
    {
        finalSalary += 100;
    }
    else if (grade == 'A')
    {
        finalSalary += 300;
    }
    else
    {
        finalSalary += 200;
    }

    printf("Luong cua nhan vien la: %d", finalSalary);
}