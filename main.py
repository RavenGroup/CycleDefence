from flask import Flask, render_template
import asyncio

app = Flask(__name__)


async def timeout():
    await asyncio.sleep(0.2)


@app.route("/home")
def main_p():
    asyncio.run(timeout())
    return render_template("homePage.html")


@app.route("/Nikita")
def about_nikita():
    asyncio.run(timeout())
    return render_template("teamMembers/Nikita_.html")


@app.route("/Alexey")
def about_alexey():
    asyncio.run(timeout())
    return render_template("teamMembers/Alexey_.html")


@app.route("/Lev")
def about_lev():
    asyncio.run(timeout())
    return render_template("teamMembers/Lev_.html")


@app.route("/Dmitriy")
def about_dmitriy():
    asyncio.run(timeout())
    return render_template("teamMembers/Dmitriy_.html")


@app.route("/about")
def about_p():
    asyncio.run(timeout())
    return render_template("aboutPage.html")


@app.route("/contacts")
def message_p():
    asyncio.run(timeout())
    return render_template("messageForm.html")


if __name__ == "__main__":
    app.run(debug=True)
