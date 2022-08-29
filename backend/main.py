import uvicorn


if __name__ == "__main__":  # start server http://0.0.0.0:8000
    uvicorn.run("server.app:app", host="0.0.0.0", port=8000, reload=True)
