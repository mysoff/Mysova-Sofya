a=int(input())
b=int(input())
while a>b-1:
    print(a-(a+1)%2)
    a-=1
    
    