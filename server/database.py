from pymongo import MongoClient
import FCM.FCMManager as fcm
# from fastapi import HTTPException
from CONFIG import NUMBER_OF_PACKAGES, USERNAME, PASSWORD, DB_NAME

MONGO_DETAILS = f"mongodb+srv://{USERNAME}:{PASSWORD}@cluster0.5awly.mongodb.net/{DB_NAME}?retryWrites=true&w=majority"  # connect to mongodb

client = MongoClient(MONGO_DETAILS)
database = client[f"{DB_NAME}"]  # connect to db
system = database["system"]  # connect to collection 'system'
user = database["user"]  # connect to collection 'user'
trash_data = database["TrashData"]  # connect to collection 'TrashData'


def convertor(latitude: str, longitude: str) -> tuple:  # reformat primary latitude and longitude to correct
    latitude = round((float(latitude[:2]) + float(latitude[2:-1]) / 60) * 1 if 'N' in latitude else -1, 7)
    longitude = round((float(longitude[:3]) + float(longitude[3:-1]) / 60) * 1 if 'E' in longitude else -1, 7)
    return latitude, longitude


def update_data(system_id, time, date, latitude, longitude, temperature, battery, gps_accuracy,
                speed, stealing):  # create checkpoint with system dates

    if system.find_one({"_id": system_id}) is None:
        system.insert_one({"_id": system_id, "stealing_status": 0})
        return "Система проинициализирована"

    if int(stealing) == 1:
        try:
            stealing_alarm(system_id, time, date)
        except Exception as e:
            print(e)

    try:
        lat_and_long = convertor(latitude, longitude)
        time = time.split('.')[0]
        speed = round(float(speed) * 463 / 900, 5)

        # if gps_accuracy == 'V':
        #     insert_trash_data(system_id, time, date, lat_and_long)
        # else:
        system.update_one(
            {
                '_id': f"{system_id}"
            },
            {
                "$push": {
                    "points": [
                        time,
                        date,
                        lat_and_long[0],
                        lat_and_long[1],
                        temperature,
                        battery,
                        speed
                    ]
                }
            }
        )
    except Exception as e:
        print(e)


def insert_trash_data(system_id, time, date, lat_and_long):  # drop to trash inaccurate data
    try:
        trash_data_id = trash_data.count_documents({}) + 1
        trash_data.insert_one(
            {
                "_id": f"{trash_data_id}",
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
        points = system.find_one({"_id": system_id},
                                 {"points": {"$slice": [int(key), NUMBER_OF_PACKAGES]}, "_id": 0, "signaling_status": 0})[
            "points"]
        last_point = system.find_one({"_id": system_id}, {"points": {"$slice": -1}, "_id": 0, "signaling_status": 0})["points"][0]
        last_sliced_point = points[-1]
        need_more = (0 if last_point == last_sliced_point else 1)
        data = {"key": int(key) + len(points), "needMore": need_more,
                "data": points}
        return data
    except Exception as e:
        print(e)


def get_current_location(user_id):  # return current point
    system_id = user.find_one({"_id": user_id})["system_id"]
    last_point = system.find_one({"_id": system_id}, {"points": {"$slice": -1}, "_id": 0, "signaling_status": 0})["points"][0]
    return last_point


def update_signaling_status(user_id, status):  # on/off signaling
    system_id = user.find_one({"_id": user_id})["system_id"]
    system.update_one(
        {
            '_id': f"{system_id}"
        },
        {
            "$set": {
                "signaling_status": status
            }
        }
    )
    print('Система поставлена на охрану') if status else print('Система снята с охраны')


def return_signaling_status(system_id):  # return status of signaling
    try:
        status = system.find_one({"_id": system_id}, {"signaling_status"})["signaling_status"]
        return status
    except Exception as e:
        print(e)


def stealing_alarm(system_id, time, date):  # call alarm if system is stolen
    try:
        fcm_token = user.find_one({"system_id": system_id}, {"fcm_token": 1, "_id": 0})["fcm_token"]
        for token in fcm_token:
            fcm.sendPush("Your bike is stolen!!!",
                         f"Time of signal: {date} {time}\nCONFIRM or CANCEL the status in the app",
                         [token])
    except Exception as e:
        print(e)


def update_token(user_id, fcm_token):
    res = user.find_one({"fcm_token": f"{fcm_token}"})
    if res is None:
        user.update_one(
            {
                '_id': f"{user_id}"
            },
            {
                "$set": {        # use $push to insert new token
                    "fcm_token": [fcm_token]
                }
            }
        )


# update_signaling_status("4", "0")
# print(return_data("88", '1169'))
# print(get_current_location("4"))
# print(system.find_one({"_id": "1"}, {"points": {"$slice": [0, 2]}}))
# update_token("4", "85487")
# print(return_signaling_status("2454"))
# stealing_alarm("2454", 5, 5)
# update_data("2454", "0", "0", "0", "0", "0", "0", "0", "0", "0")
