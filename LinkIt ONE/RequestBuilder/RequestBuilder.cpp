#include "string"
#include "iostream"
#include "array"

using namespace std;

array<string, 4> request_builder (string method, string url, string connection_type, string data) {
    array<string, 4> container;
    container[0] = std::move(method) + " / HTTP/1.1";
    container[1] = "Host: " + std::move(url);
    container[2] = "Connection: " + std::move(connection_type);
    container[3] = std::move(data);
    return container;
}

int main() {
    array<string, 4> result;
    string dat = "7685658578.07; 45749090.00";
    string meth = "Post";
    string ur = "www.wikipedia.com";
    string conn = "keep alive";
    short type;
    cin >> type;
    result = request_builder(meth, ur, conn, dat);
}