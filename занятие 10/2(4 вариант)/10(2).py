def F(A):
    k=[[0 for m in range(len(A[0]))]for m in range(len(A))]
    for i in range(len(A)):
        for j in range(len(A[i])):
            if A[i][j] < 0:
                k[i][j] = 0
            elif A[i][j] > 0:
                k[i][j] = 1
    with open("Mysova_Y-233.vivod.txt", "w") as file:
        for i in range(len(k)):
            for j in range(len(k[i])):
                if j<=i:
                    file.write(str(k[i][j])+" ")
            file.write("\n")
with open("Mysova_Y-233.vvod.txt", "r") as file:
    n=int(file.readline())
    A=[]
    for m in range(n):
        l=file.readline().strip()
        num=l.split(",")
        q=[int(nut) for nut in num]
        A.append(q)
F(A)        

                
