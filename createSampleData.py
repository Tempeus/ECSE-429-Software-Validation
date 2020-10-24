import requests
import random

URL = "http://localhost:4567"

proj = {'title': "what"}
r = requests.post(url=URL + "/projects",json=proj)


#Some TODOS
someCategories = ["Work From Home", "Groceries", "House Keeping",
"Free Time", "Extra", "Homework", "Assignments","Work From Garden"]

someProjects = ["FACC 200", "FACC 205", "FACC 206", "FACC 210", "FACC 211", "FACC 221", "FACC 222",
            "ECSE 223", "ECSE 291", "ECSE 251", "ECSE 305", "ECSE 306", "ECSE 307", "ECSE 308",
            "ECSE 309", "ECSE 310"]

someTodos = ["part1", "part2","part3", "eat", "sleep", "repeat", "game","accomplished","completed","bike","consume","fight","sell","read pt1", "read pt2", "read pt3", "read pt4", "read pt5", "read pt6", "read pt7", "read pt8", "read pt9", "read pt10",
"write pt1", "write pt2", "write pt3", "write pt4", "write pt5", "write pt6", "write pt7", "write pt8", "write pt9", "write pt10",
"test pt1", "test pt2", "test pt3", "test pt4", "test pt5", "test pt6", "test pt7", "test pt8", "test pt9", "test pt10"]

for i in range(len(someProjects)):
    projectData = {"title": someProjects[i]}
    r = requests.post(url=URL + "/projects",json=projectData)
    print(r.json())

for i in range(len(someCategories)):
    categoryData= {"title": someCategories[i]}
    r = requests.post(url=URL + "/categories",json=categoryData)
    print(r.json())

for i in range(len(someTodos)):
    todosData = {"title": someTodos[i]}
    r = requests.post(url=URL + "/todos",json=todosData)
    print(r.json())


for i in range(len(someProjects)):
    for j in range(random.randint(0,len(someCategories))):
        categoryData = {"id" : str(random.randint(2,len(someCategories)))}
        r = requests.post(url=URL + "/projects/" + str(i+1) + "/categories", json=categoryData)

for i in range(len(someProjects)):
    for j in range(random.randint(0,random.randint(0,len(someTodos)))):
        todosData = {"id" : str(random.randint(2,len(someTodos)))}
        r = requests.post(url=URL + "/projects/" + str(i+1) + "/tasks", json=todosData)


