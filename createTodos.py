import requests
import random
import sys

URL = "http://localhost:4567"

lenTodos = 1000

print("==========================TODOS=============================")

for i in range(lenTodos):
    doneStatus = True
    if (random.randint(0,1) >= 1):
        doneStatus = True
    else:
        doneStatus = False
    description = str(random.randint(0, sys.maxsize-1))
    todosData = {"title": "todo" + str(i), "doneStatus" : doneStatus,  "description" : description}
    r = requests.post(url=URL + "/todos", json=todosData)
    print(r.json())

# print("==========================CREATING RELATIONSHIP BETWEEN TODOS CATEGORIES AND PROJECTS=============================")

# for i in range(len(someProjects)):
#     for j in range(random.randint(0, len(someCategories))):
#         categoryData = {"id": str(random.randint(2, len(someCategories)))}
#         r = requests.post(url=URL + "/projects/" + str(i+1) +
#                           "/categories", json=categoryData)

# for i in range(len(someProjects)):
#     for j in range(random.randint(0, random.randint(0, len(someTodos)))):
#         todosData = {"id": str(random.randint(2, len(someTodos)))}
#         r = requests.post(url=URL + "/projects/" +
#                           str(i+1) + "/tasks", json=todosData)

# print("==============NEW PROJECTS=============")
# r = requests.get(url=URL + "/projects")
# print(r.json())
