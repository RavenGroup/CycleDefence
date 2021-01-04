from .models import CDSystem, MobileApp, SignalingStatus
from fastapi import FastAPI
from .database import update_data, return_data, signaling, update_signaling_status

app = FastAPI()  # create class object


@app.post("/CDSystem")  # system handler
def cd_system(system: CDSystem):
    try:
        print(system.system_id, system.time, system.date, system.latitude, system.longitude, system.temperature,
              system.battery, system.accuracy, system.speed)
        update_data(system.system_id, system.time, system.date, system.latitude, system.longitude,
                    system.temperature, system.battery, system.accuracy, system.speed)  # save system`s dates in db
        return signaling(system.system_id)
    except Exception as e:
        return e


@app.post("/MobileApp/UpdateData")  # mobile client handler
def mobile(application: MobileApp):
    print(application.id, application.key)
    return return_data(application.id, application.key)


@app.post("/MobileApp/Signaling/{value}")
def signaling_status_on(status: SignalingStatus, value: int):
    update_signaling_status(status.id, value)
    print(status.id, value)
