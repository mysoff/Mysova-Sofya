A=[
 [9,4,8],
 [6,6,5],
 [9,9,9]]
def f(A):
    ma=sum(A[0])
    mi=sum(A[0])
    m=A[0]
    k=A[0]
    for b in A[1:]:
        x=sum(b)
        if x>ma:
            ma=x
            m=b
        if x<ma:
            mi=x
            k=b
    return m,ma,k,mi
m,ma,k,mi=f(A)
print(m)
print(ma)
print(k)
print(mi)
