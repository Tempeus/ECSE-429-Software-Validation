import requests
import random

URL = "http://localhost:4567"

todos = requests.get(url=URL + "/todos")
projects = requests.get(url=URL + "/projects")
categories = requests.get(url=URL + "/categories")

sizeTodos = len(todos.json().get("todos"))
sizeProjects = len(projects.json().get("projects"))
sizeCategories = len(categories.json().get("categories"))

for i in range(sizeTodos):
    todosTitle = todos.json().get("todos")[i].get("title")
    r = requests.get(url=URL + "/todos?title=" +
                     todosTitle.replace(" ", "%20"))
    print(r.json())

print("==================================================")

for i in range(sizeProjects):
    projectsTitle = projects.json().get("projects")[i].get("title")
    r = requests.get(url=URL + "/projects?title=" +
                     projectsTitle.replace(" ", "%20"))
    print(r.json())

print("==================================================")

for i in range(sizeCategories):
    categoriesTitle = categories.json().get("categories")[i].get("title")
    r = requests.get(url=URL + "/categories?title=" +
                     categoriesTitle.replace(" ", "%20"))
    print(r.json())
