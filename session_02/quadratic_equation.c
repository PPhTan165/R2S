#include<stdio.h>
#include<math.h>
int main(){
    float a, b, c;
    printf("Nhap 3 so a, b,c trong phuong trinh ax^2 + bx + c =0 \n");
    scanf("%f %f %f",&a,&b,&c);

    if(a == 0 && b == 0 && c == 0){
        printf("phuong trinh vo so nghiem"); return 0;

    }else if(a == 0 && b == 0){
        printf("phuong trinh vo nghiem"); return 0;
        
    }else if(a == 0){
        printf("phuong trinh co dang bx + c =0 \nx= %.2f",(-c)/b); return 0;
    }

    float delta = b*b - (4.0*a*c);
    float x1, x2;

    if(delta < 0){
        printf("phuong trinh vo nghiem\n");
    }else if(delta == 0){
        x1 = (-b)/(2.0*a);
        printf("phuong trinh co 1 nghiem: %.2f",x1);
    }else {
        x1 = (-b + sqrt(delta))/ (2.0*a);
        x2 = (-b - sqrt(delta))/ (2.0*a);
        printf("phuong trinh co 2 nghiem phan biet: \n");
        printf("x1 = %.2f\nx2 = %.2f", x1,x2);
    }
}