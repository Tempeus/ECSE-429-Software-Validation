import requests
import random

URL = "http://localhost:4567"

lenProject = 500

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
