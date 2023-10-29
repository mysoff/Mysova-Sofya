def f(ar):
    a=[]
    for n in ar:
        if n%2!=0:
            a.append(n)
    if len(a)==0:
        print("нечетных нет")
    else:
        a.sort()
        a.reverse()
        print(a)
f([2,3,4,5])
