a=int(input())
def f():
    if (a%4==0 and a%100!=0) or a%400==0:
        return "Yes"
    else:
        return "no"
print(f())