n=int(input())
k=int(input())
a=[0,1]
for i in range (2, n+k):
    a.append(a[i-1]+a[i-2])
print(sum(a[k:k+n]))

    