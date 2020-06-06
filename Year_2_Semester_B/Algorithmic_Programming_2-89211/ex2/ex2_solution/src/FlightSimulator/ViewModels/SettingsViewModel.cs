using FlightSimulator.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace FlightSimulator.ViewModels
{
    class SettingsViewModel
    {
        private Window w;
        private SettingsModel model;
        //Constructor - get window in order to close it when finished
        public SettingsViewModel (Window window)
        {
            w = window;
            model = new SettingsModel();
        }

        public string ServerIP { get; set;}
        public string InfoPort { get; set; }
        public string CommandPort { get; set; }

        private ICommand okCommand;
        public ICommand OkCommand
        {
            get
            {
                return okCommand ?? (okCommand =
                    new CommandHandler(() => {
                        //Send to model to save the proprties
                        model.save(ServerIP, InfoPort, CommandPort);
                        //Close the settings window
                        w.Close();
                    }));
            }
            set
            {
                okCommand = value;
            }
        }


        private ICommand cancelCommand;
        public ICommand CancelCommand
        {
            get
            {
                return cancelCommand ?? (cancelCommand =
                    new CommandHandler(() => {
                        //Close the settings window without saving
                        w.Close();
                    }));
            }
            set
            {
                cancelCommand = value;
            }
        }

    }
}
