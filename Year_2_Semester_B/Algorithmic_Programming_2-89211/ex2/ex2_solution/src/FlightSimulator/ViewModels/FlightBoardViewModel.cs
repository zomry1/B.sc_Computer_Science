using FlightSimulator.Model;
using FlightSimulator.Model.Interface;
using FlightSimulator.Views;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace FlightSimulator.ViewModels
{
    public class FlightBoardViewModel : BaseNotify
    {
        //Singelton
        private static FlightBoardViewModel m_Instance = null;
        public static FlightBoardViewModel Instance
        {
            get
            {
                if (m_Instance == null)
                {
                    m_Instance = new FlightBoardViewModel();
                }
                return m_Instance;
            }
        }
        private FlightBoardModel model;


        public FlightBoardViewModel()
        {
            model = new FlightBoardModel();
            //Add to the events of the model function that update the properties
            model.PropertyChanged += (Object sender, PropertyChangedEventArgs e) =>
            {
                if (e.PropertyName == "Lat")
                {
                    Lat = model.Lat;
                }
                else if (e.PropertyName == "Lon")
                {
                    Lon = model.Lon;
                }
            };
            //Add function that will be called up when events pops
            model.addEvents((double value) =>
            {
                Lat = value;
            },
            (double value) =>
            {
                Lon = value;
            });
        }
        private double lon, lat;
        public double Lon
        {
            get
            {
                return lon;
            }

            set
            {
                lon = value;
                //NotifyPropertyChanged("Lon");
            }
        }

        public double Lat
        {
            get
            {
                return lat;
            }

            set
            {
                lat = value;
                NotifyPropertyChanged("Lat");
            }
        }


        private ICommand connectCommand;
        public ICommand ConnectCommand
        {
            get
            {
                return connectCommand ?? (connectCommand =
                    new CommandHandler(() => {
                        //Call the server to open connection
                        model.connectToServer();
                    }));
            }
            set
            {
                connectCommand = value;
            }
        }


        private ICommand settingsCommand;
        public ICommand SettingsCommand
        {
            get
            {
                return settingsCommand ?? (settingsCommand =
                    new CommandHandler(() => {    
                        //Create settings window and show it
                        SettingsView sv = new SettingsView();
                        sv.Show();
                    }));
            }
            set
            {
                settingsCommand = value;
            }
        }
    }
}
