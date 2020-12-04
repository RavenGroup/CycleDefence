#include "iostream"
#include "string"

using namespace std;

string input() {
    string body;
    int a;
    cin >> a;
    for (int i = 0; i < a; i++) {
        string local_var, double_dot, endless;
        cin >> local_var >> double_dot >> endless;
        if (i + 1 != a) {
            body += (string) "\t" + "\"" + local_var + "\"" + double_dot + "\"" + endless + "\"" + "," + "\n";
        } else {
            body += (string) "\t" + "\"" + local_var + "\"" + double_dot + "\"" + endless + "\"" + "\n";
        }
    }
    return body;
}

int main() {
    string middle = "{\n";
    middle += (input() + "}");
    cout << middle;
    return 1;
}