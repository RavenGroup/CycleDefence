from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()


class CDSystem(BaseModel):
    id: int
    userType: str
    data: str


class MobileApp(BaseModel):
    id: int
    userType: str


@app.post("/CDSystem")
def index(system: CDSystem):
    print(system.id, system.userType, system.data)
    return {"id": system.id, "userType": system.userType, "data": system.data}


@app.post("/MobileApp")
def index(system: MobileApp):
    print(system.id, system.userType)
    return {"id": system.id, "userType": system.userType}


@app.get("/")
def lev():
    return {"name": "done"}
