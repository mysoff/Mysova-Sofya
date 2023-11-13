N=4
A=[
 [3,-2,8,-2],
 [-5,6,-6,5],
 [8,-3,-11,-12],
 [-3,-2,-3,1]]
def F(A):
    k=[[0 for _ in range(len(A[0]))]for _ in range(len(A))]
    for i in range(len(A)):
        for j in range(len(A[i])):
            if A[i][j] < 0:
                k[i][j] = 0
            elif A[i][j] > 0:
                k[i][j] = 1
    for i in range(len(k)):
        for j in range(len(k[i])):
            if j<=i:
                print(k[i][j],end=" ")
        print()
F(A)