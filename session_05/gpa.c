#include <stdio.h>

#define SIZE 4

void inputMark(float marks[])
{
    char courses[SIZE][20] = {"Database", "C", "OOP", "Java"};
    for (int i = 0; i < SIZE; i++)
    {
        printf("Enter mark for %s: ", courses[i]);
        scanf("%f", &marks[i]);

        if (marks[i] < 0 || marks[i] > 10)
        {
            printf("Invalid mark");
            return;
        }
    }
}

float calculatorGPA(float marks[])
{
    float sum = 0;
    for (int i = 0; i < SIZE; i++)
    {
        sum += marks[i];
    }

    float avg10 = sum / 4;
    float gpa4 = (avg10 / 10) * 4;

    return gpa4;
}

void displayRank(float gpa)
{
    if (gpa >= 3.60 && gpa <= 4.0)
    {
        printf("Excellent\n");
    }
    else if (gpa >= 3.20)
    {
        printf("Good\n");
    }
    else if (gpa >= 2.50)
    {
        printf("Fair\n");
    }
    else if (gpa >= 2.00)
    {
        printf("Average\n");
    }
    else
    {
        printf("Weak\n");
    }
}

int main()
{
    float marks[SIZE];
    int chooseOption = 0;
    float gpa = 0;
    int hasInput = 0;
    int hasGPA = 0;
    
    while (chooseOption != 4)
    {
        printf("\n===== GPA Management =====\n");
        printf("1. Input mark\n");
        printf("2. Display GPA\n");
        printf("3. Display Rank\n");
        printf("4. Quit\n");
        printf("Choose option: ");
        scanf("%d", &chooseOption);

        switch (chooseOption)
        {
        case 1:
            inputMark(marks);
            hasInput = 1;
            hasGPA = 0;
            break;
        case 2:
            if (hasInput == 0)
            {
                printf("Input marks first.\n");
            }
            else
            {
                if (hasGPA == 0)
                {
                    gpa = calculatorGPA(marks);
                    hasGPA = 1;
                    printf("GPA: %f", gpa);
                }
            }
            break;
        case 3:
            if (hasInput == 0)
            {
                printf("Please input marks first.\n");
            }
            else
            {
                if (hasGPA == 0)
                {
                    gpa = calculatorGPA(marks);
                    hasGPA = 1;
                }

                printf("Rank: ");
                displayRank(gpa);
            }
            break;

        case 4:
            printf("Quit program.\n");
            break;

        default:
            printf("Invalid option. Please choose from 1 to 4.\n");
            break;
        }
    }

    return 0;
}