//ENUNCIADO DEL EJERCICIO

/*
Given the following requirements, write the C++ source code (choose your favorite standard) that resolves them.


Suppose we’re implementing a communication scheduling algorithm, which has two main requirements:
There should be as many simultaneous active communications as possible provided that they don’t share the same resources.
Lower priority communications give priority to higher priority communications, in other words, high priority communications cancel lower priority communications and “steal” their resources.


Resources are represented with a regular string. Communications have a name (string), a priority (int, the lower the number, the higher priority), and a list of required resources (vector<string>). When a communication is active, it’s resources are reserved for it and blocked for other communications.


Implement the “ActivateComm()” method and write in comments the expected console output of “main()”. Take into account that the provided “main()” is just an example of use case, the algorithm should work for all cases.


Initial code:


#include <iostream>
#include <vector>


using namespace std;
class Comm
{
public:
    string name;
    int priority;
    vector<string> resources;


    Comm(string n, int p, vector<string> r) : name(n), priority(p), resources(r) {}
    virtual ~Comm(){}
    void Print(){
   	 cout << "[Comm] " << name << ". Priority: " << priority << ", resources: ";
   	 for(auto r : resources)
   	 {
   	 	cout << r << " ";
   	 }
   	 cout << endl;
    }
};


//Returns the list of active communications
vector<Comm> ActivateComm(const Comm& commToBeActivated, const vector<Comm>& prevActiveComms)
{
    
}


void PrintCommList(const vector<Comm>& v)
{
	cout << "Active communications (" << v.size() << "):" << endl;
    for(auto c : v)
    {
   	 c.Print();
    }
    cout << endl;
}




int main()
{
	Comm netCom("netCom", 1, {"NETWORK", "MICROPHONE", "SPEAKER_INT"});
	Comm radioCom("radioCom", 2, {"RADIO", "MICROPHONE", "SPEAKER_EXT"});
	Comm paCom("paCom", 12, {"PA", "PA_MICROPHONE"});
	Comm paSpecialCom("paSpecCom", 13, {"PA", "PA_MICROPHONE"});
	Comm saloonCom("saloonCom", 5, {"SALOON_MIC", "SPEAKER_INT"});
	Comm baseCom("baseCom", 4, {"RADIO", "RADIO_BASE", "SPEAKER_INT"});
 
	vector<Comm> activeComms;
	activeComms = ActivateComm(baseCom, activeComms);
	PrintCommList(activeComms);
	activeComms = ActivateComm(paCom, activeComms);
	PrintCommList(activeComms);
	activeComms = ActivateComm(saloonCom, activeComms);
	PrintCommList(activeComms);
	activeComms = ActivateComm(radioCom, activeComms);
	PrintCommList(activeComms);
	activeComms = ActivateComm(netCom, activeComms);
	PrintCommList(activeComms);
	activeComms = ActivateComm(paSpecialCom, activeComms);
	PrintCommList(activeComms);
}
*/

//Returns the list of active communications
vector<Comm> ActivateComm(const Comm& commToBeActivated, const vector<Comm>& prevActiveComms)
{
    vector<Comm> newList = prevActiveComms;
    vector<int> sameResources;
    for(int i=0; i<prevActiveComms.size();i++){

        for(int j=0; j<prevActiveComms[i].resources.size(); j++){
            for (int k=0; k<commToBeActivated.resources.size(); k++){
               
                if(commToBeActivated.resources[k]==prevActiveComms[i].resources[j]){
                    sameResources.push_back(i);
                    j=prevActiveComms[i].resources.size();
                    k=commToBeActivated.resources.size();
                }
            }        
        }

    }


    if( sameResources.size()==0){
        newList.push_back(commToBeActivated);
        return newList;
    }


    for(int i=0; i< sameResources.size();i++){
        if(commToBeActivated.priority > prevActiveComms[i].priority){
            return newList;
        }
    }


    for(int i=0; i< sameResources.size();i++){
         newList.erase(newList.begin() + sameResources[i]);
    }
   
    newList.push_back(commToBeActivated);
    return newList;
}
