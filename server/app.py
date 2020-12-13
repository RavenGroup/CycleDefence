from .models import CDSystem, MobileApp
from fastapi import FastAPI
from .database import update_data, return_data

app = FastAPI()


@app.post("/CDSystem")
def index(system: CDSystem):
    print(system.id, system.data)
    return {"id": system.id, "data": system.data}


@app.post("/MobileApp")
def index(application: MobileApp):
    print(application.id)
    return return_data(application.id)
