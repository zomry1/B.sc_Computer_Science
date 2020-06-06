//
// Created by ofir8 on 12/19/18.
//

#ifndef PROJECTDEMO_MAPSHANDLER_H
#define PROJECTDEMO_MAPSHANDLER_H

#include <iostream>
#include <map>
#include <string>
#include <vector>

using namespace std;



class MapsHandler {
    static  map<string,double> addresses;
    static map<string, double> symbolTable;
    static map<string, string> binds;
    static map<string, double> notXML;
public:
    static void createAddressTable();
    static void updateFromSimulator(vector<double> params);
    static void addToAddresses(string address,double val);//
    static bool isAddressExist(string address);//
    static double getValOfAddress (string address);//
    static void addVar(string varName,double val);//
    static bool isVarExist(string varName);
    static double getVarValue (string varName);
    static void addBind(string varName,string address);
    static bool isBindExist(string varName);
    static string getVarAddress (string varName);
};



#endif //PROJECTDEMO_MAPSHANDLER_H
