age=int(input())
name=input()
if age>=16:
    print("Поздравляем вы поступили в ВГУИТ")
if age<16:
    print("Сначала нужно окончить школу!","Учиться еще",16-age)
if 0<age<75 and name!="Иван":
    print("да")
else:
    print("нет")
    