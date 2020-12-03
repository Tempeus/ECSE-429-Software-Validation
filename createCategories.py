import requests
import random
import sys
from requests.exceptions import Timeout

URL = "http://localhost:4567"

val = input("Enter your value: ") 

lenCategories = int(val)

# Some TODOS
print("==========================CATEGORIES=============================")

for i in range(lenCategories):
        description = str(random.randint(0, sys.maxsize-1))
        categoryData = {"title": "category" + str(i),  "description" : description}
        try:
            r = requests.post(url=URL + "/categories", json=categoryData, timeout=1)
            print(r.json())
        except Timeout as ex:
            print("Exception Raised: ", ex)


