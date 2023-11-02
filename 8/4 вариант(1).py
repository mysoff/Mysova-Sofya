a=int(input())
b=int(input())
c=int(input())
d=int(input())
def f(a,b):
    while b:
        a,b=b,a%b
    return a
def f1(a,b,c,d):
    k=a*d
    l=b*c
    p=f(k,l)
    k=k//p
    l=l//p
    return k,l
print(f1(a,b,c,d))