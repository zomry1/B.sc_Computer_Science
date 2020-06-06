using FlightSimulator.Model;
using FlightSimulator.ViewModels;
using FlightSimulator.Views;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace FlightSimulator
{
    enum flightControl
    {
        Aileron,
        Rudder,
        Elevator,
        Throttle
    }
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            flightBoard.DataContext = FlightBoardViewModel.Instance;
        }

        protected override void OnClosing(CancelEventArgs e)
        {
            //Close InfoServer
            InfoServer.Instance.disconnect();
            base.OnClosing(e);
        }




    }
}
