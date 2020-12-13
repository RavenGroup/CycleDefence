from pymongo import MongoClient
from datetime import datetime

MONGO_DETAILS = "mongodb://localhost:27017"
client = MongoClient(MONGO_DETAILS)
database = client["BiTech"]
system = database["system"]
user = database["user"]

# system_id = '111'
# time_stamp = datetime.now().strftime('%Y-%m-%d %H:%M:%S')


def update_data(system_id, time_stamp):
    system.update_one(
        {
            '_id': f"{system_id}"
        },
        {
            "$set":
            {
                f"{time_stamp}":
                    {
                        "position": 'я тут',
                        "temperature": '14'
                    }
            }
        }
    )


def return_data(user_id):
    try:
        system_id = user.find_one({"_id": user_id})["system_id"]
        data = system.find_one({"_id": system_id}, {"_id": 0})
        print(data)
        return data
    except Exception as e:
        print(e)
