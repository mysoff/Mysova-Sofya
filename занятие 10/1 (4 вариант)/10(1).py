with open("Mysova_Y-233_vvod") as file1:
    n = file1.read().split()
A = []
j = 1
for i in range(int(n[0])):
    B = []
    for i in range(int(n[0])):
        B.append(int(n[j]))
        j += 1
    A.append(B)
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
    print(m)
    print(ma)
    print(k)
    print(mi)
    with open("Mysova_Y-233_vivod",'w') as file2:
        for i in range(len(m)):
            file2.write(str(m[i]))
        file2.write('\n'+str(ma)+'\n')
        for i in range(len(k)):
            file2.write(str(k[i]))
        file2.write('\n'+str(mi))
f(A)

