#! /bin/bash
g++ -I/usr/lib/jvm/java-7-openjdk-amd64/include/ src/c++/loadlib_LoadLib.cpp -fPIC -shared -o libLoadLib.so
