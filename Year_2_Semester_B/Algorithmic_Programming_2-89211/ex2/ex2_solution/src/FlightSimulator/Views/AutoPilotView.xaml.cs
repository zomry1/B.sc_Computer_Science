﻿using FlightSimulator.ViewModels;
using System;
using System.Collections.Generic;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace FlightSimulator.Views
{
    /// <summary>
    /// Interaction logic for AutoPilotView.xaml
    /// </summary>
    public partial class AutoPilotView : UserControl
    {
        private AutoPilotViewModel APVM = new AutoPilotViewModel();
        public AutoPilotView()
        {
            InitializeComponent();
            AutoPilot.DataContext = APVM;
        }
    }
}
