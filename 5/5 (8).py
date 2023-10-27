def f():
    a=int(input())
    i=1
    m=1
    while a!=0:
        n=int(input())
        if n==a:
            i+=1
        else:
            if i>m:
                m=i
            if n==0:
                break
            i=1
        a=n
    print(m)
f()
        