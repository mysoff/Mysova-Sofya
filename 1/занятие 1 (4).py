seconds=int(input())
d=seconds//86400
h=seconds%86400//3600
m=seconds%3600//60
s=seconds%60
print(d,h,m,s)
