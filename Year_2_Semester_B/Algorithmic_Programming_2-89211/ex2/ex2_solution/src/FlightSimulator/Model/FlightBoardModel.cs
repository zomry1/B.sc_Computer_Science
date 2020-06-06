using FlightSimulator.ViewModels;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FlightSimulator.Model
{
    class FlightBoardModel : BaseNotify
    {

        private double lon;
        public double Lon
        {
            get { return lon; }

            set
            {
                lon = value;
                NotifyPropertyChanged("Lon");
            }
        }
        private double lat;
        public double Lat
        {
            get { return lat; }

            set
            {
                lat = value;
                NotifyPropertyChanged("Lat");
            }
        }

        //Sent to the server to open connection
        public void connectToServer()
        {
            InfoServer.Instance.connect();
        }

        //Add functions that will call up when LatChanged and LonChanged are called
        public void addEvents(PropertyEvent lat, PropertyEvent lon)
        {
            InfoServer.Instance.LatChanged += lat;
            InfoServer.Instance.LonChanged += lon;
        }
    }
}
