import requests
import random

URL = "http://localhost:4567"

# Some TODOS
someCategories = ["Work From Home", "Groceries", "House Keeping",
                  "Free Time", "Extra", "Homework", "Assignments", "Work From Garden"]

someProjects = ["FACC 200", "FACC 205", "ECSE 308",
                "ECSE 309", "ECSE 310"]

someTodos = ["read"
             "write pt1", "write pt2",
             "test pt1", "test pt2"]

for i in range(len(someProjects)):
    projectData = {"title": someProjects[i]}
    r = requests.post(url=URL + "/projects", json=projectData)
    print(r.json())

for i in range(len(someCategories)):
    categoryData = {"title": someCategories[i]}
    r = requests.post(url=URL + "/categories", json=categoryData)
    print(r.json())

for i in range(len(someTodos)):
    todosData = {"title": someTodos[i]}
    r = requests.post(url=URL + "/todos", json=todosData)
    print(r.json())


for i in range(len(someProjects)):
    for j in range(random.randint(0, len(someCategories))):
        categoryData = {"id": str(random.randint(2, len(someCategories)))}
        r = requests.post(url=URL + "/projects/" + str(i+1) +
                          "/categories", json=categoryData)

for i in range(len(someProjects)):
    for j in range(random.randint(0, random.randint(0, len(someTodos)))):
        todosData = {"id": str(random.randint(2, len(someTodos)))}
        r = requests.post(url=URL + "/projects/" +
                          str(i+1) + "/tasks", json=todosData)

print("==============NEW PROJECTS=============")
r = requests.get(url=URL + "/projects")
print(r.json())
