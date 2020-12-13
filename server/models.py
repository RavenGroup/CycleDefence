from pydantic import BaseModel


class CDSystem(BaseModel):
    id: str
    data: str


class MobileApp(BaseModel):
    id: str
