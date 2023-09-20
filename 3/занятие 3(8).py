a=int(input())
b=int(input())
c=int(input())
def f():
    if a==b==c:
        return 3
    if a==b or b==c or a==c:
        return 2
    else:
        return 0
print(f())