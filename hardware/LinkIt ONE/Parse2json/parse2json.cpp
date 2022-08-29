#include "iostream"
#include "string"
#include "vector"

using namespace std;

string json;

void parse2json(const string& key, const string& meaning, unsigned short t, unsigned short len) {
    if (t == len - 2)
        json.append("\t\"" + key + "\": \"" + meaning + "\"\n");
    else
        json.append("\t\"" + key + "\": \"" + meaning + "\",\n");
}

void distributor(vector<string> info) {
    unsigned short len = info.size();
    json += "{\n";
    for (unsigned short i = 0; i < len; i += 2)
        parse2json(info[i], info[i + 1], i, len);
    json.append("}");
}

int main() {
    vector<string> starter = {"method", "post", "connecting", "lost", "data", "... all ...", "style", "1"};
    distributor(starter);
    cout << json;
    return 0;
}