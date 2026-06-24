#include <stdio.h>
#include <ctype.h>

int main()
{

    char code;

    printf("Nhap ky tu (b,c,f,p,v): ");
    scanf("%c", &code);

    char newCode = tolower(code);

    switch (newCode)
    {
    case 'b':
        printf("Basic");
        break;
    case 'c':
        printf("Cobol");
        break;
    case 'f':
        printf("Fortran");
        break;
    case 'p':
        printf("Pascal");
        break;
    case 'v':
        printf("Visual C++");
    default:
        break;
    }
}