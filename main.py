from flask import Flask, render_template

app = Flask(__name__)


@app.route("/home")
def main_p():
    return render_template("homePage.html")


@app.route("/about")
def about_p():
    return render_template("aboutPage.html")


@app.route("/contacts")
def message_p():
    return render_template("messageForm.html")


if __name__ == "__main__":
    app.run(debug=True)
