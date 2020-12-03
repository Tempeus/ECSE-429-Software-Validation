import requests
import random
import sys

URL = "http://localhost:4567"

lenCategories = 500

# Some TODOS
print("==========================CATEGORIES=============================")

for i in range(lenCategories):
    description = str(random.randint(0, sys.maxsize-1))
    categoryData = {"title": "category" + str(i),  "description" : description}
    r = requests.post(url=URL + "/categories", json=categoryData)
    print(r.json())

