
def f(n):
    max=0
    k=0
    for i in range(len(n)):
        if n[i]>max:
            max=n[i]
            k=i
    return max,k
print(f([2,4,5]))


        
    