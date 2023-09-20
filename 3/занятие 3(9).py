n=int(input())
m=int(input())
k=int(input())
def f():
    if n*m>k and (k%n==0 or k%m==0):
        return "Yes"
    else:
        return "NO"
print(f())