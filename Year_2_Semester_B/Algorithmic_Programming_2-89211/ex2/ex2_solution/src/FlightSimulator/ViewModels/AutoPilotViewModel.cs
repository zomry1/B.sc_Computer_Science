using FlightSimulator.Model;
using System;
using System.Windows.Input;
using System.Windows.Media;

namespace FlightSimulator.ViewModels
{
    class AutoPilotViewModel : BaseNotify
    {
        private AutoPilotModel model = new AutoPilotModel();
        private string commands;

        public bool Color
        {
            get;
            set;
        }

        public String Commands
        {
            get
            {
                return commands;
            }
            set
            {
                commands = value;
                if(commands != "")
                {
                    //Change color to true - not empty
                    Color = true;
                    NotifyPropertyChanged("Color");
                }
            }
        }
        ///*
        private ICommand clearCommand;
        public ICommand ClearCommand
        {
            get
            {
                return clearCommand ?? (clearCommand =
                    new CommandHandler(() => {
                        //Clear the textbox
                        Commands = "";
                        NotifyPropertyChanged(Commands);
                        //Change color to false - empty
                        Color = false;
                        NotifyPropertyChanged("Color");
                    }));
            }
            set
            {
                clearCommand = value;
            }
        }

        private ICommand okCommand;
        public ICommand OKCommand
        {
            get
            {
                return okCommand ?? (okCommand =
                    new CommandHandler(() => {
                        //Send the commadns to the model in order to send to the simulator
                        model.SendCommand(Commands);
                        //Change color to false - like empty
                        Color = false;
                        NotifyPropertyChanged("Color");
                    }));
            }
            set
            {
                clearCommand = value;
            }
        }


    }
}
