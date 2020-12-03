import requests
import random

URL = "http://localhost:4567"

lenCategories = 500

# Some TODOS
print("==========================CATEGORIES=============================")

for i in range(lenCategories):
    description = str(random.randint(0, sys.maxsize-1))
    categoryData = {"title": "category" + str(i), "description" : description}
    r = requests.post(url=URL + "/categories", json=categoryData)
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
