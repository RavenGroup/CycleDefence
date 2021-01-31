from .models import CDSystem, MobileApp, System_ID, Login
from fastapi import FastAPI
from .database import update_data, return_data, return_signaling_status, update_signaling_status, update_token

app = FastAPI()  # create class object


@app.post("/CDSystem")  # system handler
def cd_system(system: CDSystem):
    try:
        print(system.system_id, system.time, system.date, system.latitude, system.longitude, system.temperature,
              system.battery, system.accuracy, system.speed, system.stealing)
        update_data(system.system_id, system.time, system.date, system.latitude, system.longitude,
                    system.temperature, system.battery, system.accuracy, system.speed,
                    system.stealing)  # save system`s dates in db
        return return_signaling_status(system.system_id)
    except Exception as e:
        return e


@app.post("/MobileApp/UpdateData")  # mobile client handler
def mobile(application: MobileApp):
    print(application.id, application.key)
    return return_data(application.id, application.key)


@app.post("/Signaling/{value}")  # system data reception
def signaling_status_on(system: System_ID, value: int):
    try:
        update_signaling_status(system.id, value)
        print(system.id, value)
    except Exception as e:
        print(e)


@app.post("/MobileApp/Login")  # mobile client handler
def mobile(user: Login):
    try:
        print(user.id, user.fcm_token)
        update_token(user.id, user.fcm_token)
        return {"status": "OK"}
    except Exception as e:
        print(e)
