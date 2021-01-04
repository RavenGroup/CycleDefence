from pymongo import MongoClient
from numpy import fromiter, array
from json import dumps
from fastapi import HTTPException
from CONFIG import NUMBER_OF_PACKAGES, USERNAME, PASSWORD, DB_NAME

MONGO_DETAILS = f"mongodb+srv://{USERNAME}:{PASSWORD}@cluster0.5awly.mongodb.net/{DB_NAME}?retryWrites=true&w=majority"  # connect to mongodb
client = MongoClient(MONGO_DETAILS)
database = client["BiTech"]  # connect to db
system = database["system"]  # connect to collection 'system'
user = database["user"]  # connect to collection 'user'
trash_data = database["TrashData"]  # connect to collection 'TrashData'


def convertor(latitude: str, longitude: str) -> tuple:
    latitude = round((float(latitude[:2]) + float(latitude[2:-1]) / 60) * 1 if 'N' in latitude else -1, 7)
    longitude = round((float(longitude[:3]) + float(longitude[3:-1]) / 60) * 1 if 'E' in longitude else -1, 7)

    return latitude, longitude


def update_data(system_id, time, date, latitude, longitude, temperature, battery, gps_accuracy,
                speed):  # create checkpoint with system dates
    # if system.find_one({"_id": system_id}) is None:
    #     system.insert_one({"_id": system_id})
    #     return "Система проинициализирована"
    lat_and_long = convertor(latitude, longitude)
    time = time.split('.')[0]
    speed = round(float(speed) * 463 / 900, 5)
    if gps_accuracy == 'V': insert_trash_data(system_id, time, date, lat_and_long)
    system.update_one(
        {
            '_id': f"{system_id}"
        },
        {
            "$set":
                {
                    f"{len(system.find_one({'_id': f'{system_id}'})) - 1}":
                        {
                            "time": time,
                            "date": date,
                            "lat": lat_and_long[0],
                            "long": lat_and_long[1],
                            "temp": temperature,
                            "battery": battery,
                            "speed": speed
                        }
                }
        }
    )


def insert_trash_data(system_id, time, date, lat_and_long):
    try:
        id = trash_data.count_documents({}) + 1
        trash_data.insert_one(
            {
                "_id": f"{id}",
                "system_id": system_id,
                "time": time,
                "date": date,
                "lat": lat_and_long[0],
                "long": lat_and_long[1],
            }
        )
    except Exception as e:
        print(e)


def return_data(user_id, key):  # return checkpoints to MobileApp
    try:
        system_id = user.find_one({"_id": user_id})["system_id"]
        data = system.find_one({"_id": system_id}, {"_id": 0, "signaling_status": 0})
        last_point = list(data.keys())[-1]
        last_sliced_point = list(data.keys())[int(key):int(key) + NUMBER_OF_PACKAGES][-1]
        need_more = (0 if last_point == last_sliced_point else 1)
        a = [[*point.values()] for point in [*data.values()][int(key):int(key) + NUMBER_OF_PACKAGES]]
        data = {"key": last_sliced_point, "needMore": need_more,
                "data": a}
        return data
    except Exception as e:
        print(e)
        # raise HTTPException(status_code=404, detail="System not found")


def getCurrentLocation(user_id):
    system_id = user.find_one({"_id": user_id})["system_id"]
    data = system.find_one({"_id": system_id}, {"_id": 0, "signaling_status": 0})
    last_point = [*[*data.values()][-1].values()]
    return last_point


def update_signaling_status(user_id, status):
    system_id = user.find_one({"_id": user_id})["system_id"]
    system.update_one(
        {
            '_id': f"{system_id}"
        },
        {
            "$set":
                {
                    "signaling_status": status
                }
        }
    )
    print('Система поставлена на охрану') if status else print('Система снята с охраны')


def signaling(system_id):
    status = system.find_one({"_id": system_id})["signaling_status"]
    print(status)
    return status


# update_signaling_status("4", "0")
# system.insert_one({"_id": "2454", "signaling_status": 0})
# insert_trash_data('658', '55:55', "20.02.5555", (654456, 4141414))
# print(return_data("4", '882'))
# print(getCurrentLocation("4"))
