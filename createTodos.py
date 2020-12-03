import requests
import random
import sys
from requests.exceptions import Timeout

URL = "http://localhost:4567"

val = input("Enter your value: ") 

lenTodos = int(val)

print("==========================TODOS=============================")

for i in range(lenTodos):
    doneStatus = True
    if (random.randint(0,1) >= 1):
        doneStatus = True
    else:
        doneStatus = False
    description = str(random.randint(0, sys.maxsize-1))
    todosData = {"title": "todo" + str(i), "doneStatus" : doneStatus,  "description" : description}
    try:
        r = requests.post(url=URL + "/todos", json=todosData, timeout=1)
        print(r.json())
    except Timeout as ex:
        print("Exception Raised: ", ex)