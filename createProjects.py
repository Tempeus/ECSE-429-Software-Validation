import requests
import random
import sys

URL = "http://localhost:4567"
val = input("Enter your value: ") 

lenProject = int(val)

# Some TODOS
print("==========================PROJECTS=============================")             

for i in range(lenProject):
    completed = True
    active = True
    if (random.randint(0,1) >= 1):
        completed = True
    else:
        completed = False
    if (random.randint(0,1) >= 1):
        active = True
    else:
        active = False
    description = str(random.randint(0, sys.maxsize-1))
    projectData = {"title": "project" + str(i),"completed" : completed, "active" : active, "description" : description}
    r = requests.post(url=URL + "/projects", json=projectData)
    print(r.json())
