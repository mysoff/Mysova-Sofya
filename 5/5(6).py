def f():
   k=0
   len=0
   a=int(input())
   while a!=0:
       k+=a
       len+=1
       a=int(input())
   print(k/len)
f()