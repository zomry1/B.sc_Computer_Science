using FlightSimulator.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FlightSimulator.ViewModels
{
    class ManualViewModel : BaseNotify
    {
        private double aileron = 0, throttle = 0, elevator = 0, rudder = 0;
        private ManualModel model = new ManualModel();
        public double Alieron
        {
            get
            {
                return aileron;
            }
            set
            {
                //If not the same value - update it, notifyPropertyChanged, send it to the model
                if (aileron != value)
                {
                    aileron = value;
                    NotifyPropertyChanged("aileron");
                    model.SendCommand(flightControl.Aileron, aileron);
                }
            }
        }

        public double Throttle
        {
            get
            {
                return throttle;
            }
            set
            {
                //If not the same value - update it, notifyPropertyChanged, send it to the model
                if (throttle != value)
                {
                    throttle = value;
                    NotifyPropertyChanged("throttle");
                    model.SendCommand(flightControl.Throttle, throttle);
                }
            }
        }

        public double Rudder
        {
            get
            {
                return rudder;
            }
            set
            {
                //If not the same value - update it, notifyPropertyChanged, send it to the model
                if (rudder != value)
                {
                    rudder = value;
                    NotifyPropertyChanged("Rudder");
                    model.SendCommand(flightControl.Rudder, rudder);
                }
            }
        }

        public double Elevator
        {
            get
            {
                return elevator;
            }
            set
            {
                if(elevator != value)
                {
                    //If not the same value - update it, notifyPropertyChanged, send it to the model
                    elevator = value;
                    NotifyPropertyChanged("Elevator");
                    model.SendCommand(flightControl.Elevator, elevator);
                }
            }
        }

    }
}
