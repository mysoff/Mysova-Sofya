x=2
y=5
rad=7
p=(-2,2)
k=(6,12)
l=(-5,5)
def f(x,y,a,b,r):
    bb=(x-a)**2+(y-b)**2
    return bb<=r**2
def f1(p,k,l,a,b,r):
    c=0
    if f(p[0],p[1],a,b,r):
        c+=1
    if f(k[0],k[1],a,b,r):
        c+=1
    if f(l[0],l[1],a,b,r):
        c+=1
    return c
print(f1(p,k,l,x,y,rad))