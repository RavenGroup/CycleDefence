from flask import Flask, render_template

import time

app = Flask(__name__)


@app.route("/home")
def main_p():
    time.sleep(0.5)
    return render_template("homePage.html")


@app.route("/about")
def about_p():
    time.sleep(0.5)
    return render_template("aboutPage.html")


@app.route("/contacts")
def message_p():
    time.sleep(0.5)
    return render_template("messageForm.html")


if __name__ == "__main__":
    app.run(debug=True)
