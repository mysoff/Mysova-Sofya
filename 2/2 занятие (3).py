import math
x=3.74*10**-2
y=-0.825
z=0.16*10**2
a=(1+math.pow(math.sin(x+y),2))/(math.fabs(x-(2*y)/(1+(x**2)*(y**2))))*math.pow(x,math.fabs(y))+(math.pow(math.cos(math.atan(1/z)),2))
print(a)