#include "string"
#include "iostream"
#include "array"

using namespace std;


array<string, 4> request_builder (string data, string method, string url, string conection_type) {
    array<string, 4> container;
    int i = 0;
    container[i] = std::move(data);
    i++;
    container[i] = std::move(method);
    i++;
    container[i] = std::move(url);
    i++;
    container[i] = std::move(conection_type);
    return container;
}

void data_out(string data, string method, string url, string conection_type, short style) {
    array<string, 4> after  = request_builder(std::move(data), std::move(method), std::move(url), std::move(conection_type));
    if (style == 1)
        for (int i = 0; i < 4; i++)
            cout << after[i] + "\n";
    else
        for (int i = 0; i < 4; i++)
            cout << after[i] + "\t\t";

}


int main() {
    array<string, 4> result;
    string dat = "\"data variable\"";
    string metho = "\"method of ...\"";
    string ur = "\"your favorite website\"";
    string con = "\"con_type 10\"";
    short type;
    //enter type of output
    cin >> type; //all numbers without "1" will output array by \n, else it will be otputed by \t 
    data_out(dat, metho, ur, con, type);
}