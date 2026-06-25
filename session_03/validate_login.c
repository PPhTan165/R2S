#include <stdio.h>

const int CORRECT_PASSWORD = 12345;

int main()
{
    int password;
    int invalid = 0;

    while (invalid < 3)
    {
        printf("Nhap mat khau: ");
        scanf("%d", &password);
        printf("\n");

        if (password != CORRECT_PASSWORD)
        {
            invalid++;
            printf("Invalid password\n");
            if (invalid == 3)
            {
                printf("3 incorrect attemps\n");
            }
        }
        else
        {
            printf("Login successful\n");
            break;
        }
    }

    return 0;
}