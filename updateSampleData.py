# This is a program to test specific instances of tasks/projects and categories
# This also includes adding and deleting relationship between objects

import requests
import random
import json

URL = "http://localhost:4567"


todos = requests.get(url=URL + "/todos")
projects = requests.get(url=URL + "/projects")
categories = requests.get(url=URL + "/categories")

# print(len(r.json().get("todos")))
sizeTodos = len(todos.json().get("todos"))
sizeProjects = len(projects.json().get("projects"))
sizeCategories = len(categories.json().get("categories"))

# POST /todos/:id
print("===================POST /todo/:id=======================")

todosJson = todos.json().get("todos")
for i in range(sizeTodos):
    todosID = todosJson[i].get("id")
    todosData = {"description": "POST ammend " +
                 todosJson[i].get("title") + ". ID#: " + todosID}
    r = requests.post(url=URL + "/todos/" + todosID, json=todosData)

todos = requests.get(url=URL + "/todos")
print(todos.json())
print("====================PUT /todos/:id======================")

todos = requests.get(url=URL + "/todos")
todosJson = todos.json().get("todos")
# PUT /todos/:id
sizeTodos = len(todos.json().get("todos"))
for i in range(sizeTodos):
    todosID = todosJson[i].get("id")
    todosData = {"description": "PUT ammend " +
                 todosJson[i].get("title") + ". ID#: " + todosID}
    r = requests.put(url=URL + "/todos/" + todosID, json=todosData)

todos = requests.get(url=URL + "/todos")
print(todos.json())
print("====================POST /todos/:id/categories======================")


categories = requests.get(url=URL + "/categories")
categoriesJson = categories.json().get("categories")
# POST /todos/:id/categories
todosJson = todos.json().get("todos")
sizeTodos = len(todos.json().get("todos"))
for i in range(sizeTodos):
    todosID = todosJson[i].get("id")
    try:
        todosData = {"id": categoriesJson[0].get("id")}
        r = requests.post(url=URL + "/todos/" + todosID +
                          "/categories", json=todosData)
    except:
        print("NO CATEGORIES")

todos = requests.get(url=URL + "/todos")
print(todos.json())
print("====================DELETE /todos/:id/categories/:id======================")

# DELETE /todos/:id/categories/:id
# if has category DELETE first category of task
todos = requests.get(url=URL + "/todos")
todosJson = todos.json().get("todos")
sizeTodos = len(todos.json().get("todos"))
for i in range(sizeTodos):
    todosID = todosJson[i].get("id")
    try:
        r = requests.delete(url=URL + "/todos/" + todosID + "/categories/" +
                            requests.get(url=URL + "/todos").json().get("todos")[i].get("categories")[0].get("id"))
    except:
        print("TODOS " + todosID + " has no categories")

todos = requests.get(url=URL + "/todos")
print(todos.json())
print("====================POST /todos/:id/taskof======================")


projects = requests.get(url=URL + "/projects")
projectsJson = projects.json().get("projects")
# POST /todos/:id/projects
todos = requests.get(url=URL + "/todos")
todosJson = todos.json().get("todos")
sizeTodos = len(todos.json().get("todos"))
for i in range(sizeTodos):
    todosID = todosJson[i].get("id")
    try:
        todosData = {"id": projectsJson[0].get("id")}
        r = requests.post(url=URL + "/todos/" + todosID +
                          "/tasksof", json=todosData)
    except:
        print("NO PROJECTS")

todos = requests.get(url=URL + "/todos")
print(todos.json())

print("====================DELETE /todos/:id/projects/:id======================")
# DELETE /todos/:id/projects/:id
# if has project DELETE first projects of task
projects = requests.get(url=URL + "/projects")
projectsJson = projects.json().get("projects")
todos = requests.get(url=URL + "/todos")
todosJson = todos.json().get("todos")
for i in range(sizeTodos):
    todosID = todosJson[i].get("id")
    try:
        r = requests.delete(url=URL + "/todos/" + todosID + "/projects/" +
                            requests.get(url=URL + "/todos").json().get("todos")[i].get("tasksof")[0].get("id"))
    except:
        print("TODOS " + todosID + " has no projects")

todos = requests.get(url=URL + "/todos")
print(todos.json())


print("===================POST /projects/:id=======================")
# POST /projects/:id
projects = requests.get(url=URL + "/projects")
projectsJson = projects.json().get("projects")
sizeProjects = len(projects.json().get("projects"))
todos = requests.get(url=URL + "/todos")
for i in range(sizeProjects):
    projectsID = projectsJson[i].get("id")
    projectData = {"description": "POST ammend " +
                   projectsJson[i].get("title") + ". ID#: " + projectsID}
    r = requests.post(url=URL + "/projects/" + projectsID, json=projectData)

projects = requests.get(url=URL + "/projects")

print(projects.json())

print("===================PUT /projects/:id=======================")
# POST /projects/:id
projects = requests.get(url=URL + "/projects")
projectsJson = projects.json().get("projects")
sizeProjects = len(projects.json().get("projects"))
for i in range(sizeProjects):
    projectsID = projectsJson[i].get("id")
    projectData = {"description": "PUT ammend " +
                   projectsJson[i].get("title") + ". ID#: " + projectsID}
    r = requests.post(url=URL + "/projects/" + projectsID, json=projectData)

projects = requests.get(url=URL + "/projects")

print(projects.json())

print("====================POST /projects/:id/tasks======================")

# POST /projects/:id/tasks
todos = requests.get(url=URL + "/todos")
todosJson = todos.json().get("todos")

projects = requests.get(url=URL + "/projects")
projectsJson = projects.json().get("projects")
sizeProjects = len(projects.json().get("projects"))
for i in range(sizeProjects):
    projectsID = projectsJson[i].get("id")
    try:
        projectData = {"id": todosData[0].get("id")}
        r = requests.post(url=URL + "/projects/" + projectsID +
                          "/tasks", json=projectData)
    except:
        print("NO TASKS")

todos = requests.get(url=URL + "/projects")
print(todos.json())

print("====================DELETE /projects/:id/tasks/:id======================")

# DELETE /todos/:id/categories/:id
# if has category DELETE first category of task
todos = requests.get(url=URL + "/todos")
todosJson = todos.json().get("todos")
projects = requests.get(url=URL + "/projects")
projectsJson = projects.json().get("projects")
sizeProjects = len(projects.json().get("projects"))
for i in range(sizeProjects):
    projectsID = projectsJson[i].get("id")
    try:
        r = requests.delete(url=URL + "/projects/" + projectsID + "/tasks/" +
                            requests.get(url=URL + "/projects").json().get("tasks")[0].get("id"))
    except:
        print("PROJECTS " + projectsID + " has no tasks")

projects = requests.get(url=URL + "/projects")
print(projects.json())

