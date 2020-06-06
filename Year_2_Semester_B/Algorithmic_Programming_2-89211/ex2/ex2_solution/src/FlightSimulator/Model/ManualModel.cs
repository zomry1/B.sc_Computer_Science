using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FlightSimulator.Model
{
    class ManualModel
    {
        private Client cl = new Client();

        //Defines for commands
        private const string alieron = "set /controls/flight/aileron ";
        private const string elevator = "set /controls/flight/elevator ";
        private const string rudder = "set /controls/flight/rudder ";
        private const string throttle = "set /controls/engines/current-engine/throttle ";
        public void SendCommand(flightControl fc , double value)
        {
            //Check which flightControl changed adn sent it to the client.
            switch (fc)
            {
                case flightControl.Aileron:
                    cl.addCommand(alieron + value.ToString() + "\r\n");
                    break;

                case flightControl.Elevator:
                    cl.addCommand(elevator + value.ToString() + "\r\n");
                    break;

                case flightControl.Rudder:
                    cl.addCommand(rudder + value.ToString() + "\r\n");
                    break;

                case flightControl.Throttle:
                    cl.addCommand(throttle + value.ToString() + "\r\n");
                    break;
            }
        }
    }
}
