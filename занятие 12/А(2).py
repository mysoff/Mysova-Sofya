x=int(input())
y=int(input())
def f(x,y):
    if x<y:
        return x
    else:
        return f(x-y,y)
print(f(x,y))

