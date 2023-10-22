def f():
   a=int(input())
   k=0
   while a!=0:
       i=int(input())
       if i!=0 and a<i:
           k+=1
       a=i
   print(k)
f()