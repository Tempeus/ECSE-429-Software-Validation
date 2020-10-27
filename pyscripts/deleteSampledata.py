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

print(todos.json())
print()
print(projects.json())
print()
print(categories.json())
print()
print("OLD len todos: " + str(sizeTodos))
print("OLD len projects: " + str(sizeProjects))
print("OLD len categories: " + str(sizeCategories))
print("===========================DELETE==============================")
print()
print()

for i in range(1, sizeTodos//2 + 1):
    requests.delete(url=URL + "/todos/" + str(i))

todos = requests.get(url=URL + "/todos")
print(todos.json())
print()

for i in range(1, sizeProjects//2 + 1):
    requests.delete(url=URL + "/projects/" + str(i))

projects = requests.get(url=URL + "/projects")
print(projects.json())
print()

for i in range(1, sizeCategories//2 + 1):
    requests.delete(url=URL + "/categories/" + str(i))

categories = requests.get(url=URL + "/categories")
print(categories.json())
print()
sizeTodos = len(todos.json().get("todos"))
sizeProjects = len(projects.json().get("projects"))
sizeCategories = len(categories.json().get("categories"))
print("NEW len todos: " + str(sizeTodos))
print("NEW len projects: " + str(sizeProjects))
print("NEW len categories: " + str(sizeCategories))
