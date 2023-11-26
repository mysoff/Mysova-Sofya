
def f():
    a=int(input())
    if a==0:
        return 0
    m=f()
    if a>m:
        return a
    else:
        return m
print(f())
    