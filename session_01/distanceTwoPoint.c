#include<stdio.h>
#include<math.h>

int main() {
    int x1,y1,x2,y2;
    float distance;

    printf("Nhap toa do (x1,y1): ");
    scanf("%d %d",&x1,&y1);
    
    printf("Nhap toa do (x2,y2): ");
    scanf("%d %d",&x2,&y2);

    distance =sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));

    printf("Khoang cach giua 2 diem (x1,y1) va (x2,y2) la: %f",distance);
}