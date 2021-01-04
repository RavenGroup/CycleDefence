from pydantic import BaseModel


class CDSystem(BaseModel):      # data structure for system
    system_id: str
    time: str
    date: str
    latitude: str
    longitude: str
    temperature: str
    battery: str
    accuracy: str
    speed: str


class MobileApp(BaseModel):     # data structure for mobile app
    id: str
    key: str


class SignalingStatus(BaseModel):
    id: str
