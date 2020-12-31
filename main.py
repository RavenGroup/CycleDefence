from flask import Flask, render_template
import asyncio

app = Flask(__name__)


async def timeout():
    await asyncio.sleep(0.2)


@app.route("/home")
def main_p():
    asyncio.run(timeout())
    return render_template("homePage.html")


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
