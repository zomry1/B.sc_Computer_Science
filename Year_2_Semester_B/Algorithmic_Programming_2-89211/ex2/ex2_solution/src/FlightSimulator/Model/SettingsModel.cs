using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace FlightSimulator.Model
{
    class SettingsModel
    {
        //Update the ApplictionSettingsModel - parse if needed
        public void save(string ServerIP, string InfoPort, string CommandPort)
        {
            if(ServerIP != "")
            {
                ApplicationSettingsModel.Instance.FlightServerIP = ServerIP;
            }
            try
            {
                if(InfoPort != "")
                {
                    ApplicationSettingsModel.Instance.FlightInfoPort = int.Parse(InfoPort);
                }
                if(CommandPort != "")
                {
                    ApplicationSettingsModel.Instance.FlightCommandPort = int.Parse(CommandPort);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            ApplicationSettingsModel.Instance.SaveSettings();
        }
    }
}
