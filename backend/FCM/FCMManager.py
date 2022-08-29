from os import path
from firebase_admin import credentials, messaging
import firebase_admin

parent_dir = path.dirname(path.abspath(__file__))
cred = credentials.Certificate(parent_dir + "/serviceAccountKey.json")
firebase_admin.initialize_app(cred)


def sendPush(title, msg, registration_token):
    # See documentation on defining a message payload.
    message = messaging.MulticastMessage(
        notification=messaging.Notification(
            title=title,
            body=msg
        ),
        tokens=registration_token,
    )

    # Send a message to the device corresponding to the provided
    # registration token.
    response = messaging.send_multicast(message)
    # Response is a message ID string.
    print('Successfully sent message:', response)
