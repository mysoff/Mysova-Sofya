n=int(input())
f1=f2=1
k=3
f3=0
s=2
while k<=n:
    f3=f1+f2
    s=f3+s
    f1=f2
    f2=f3
    k+=1
print(s)
    