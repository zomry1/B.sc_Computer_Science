using FlightSimulator.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Windows;
using System.Net.Sockets;
using System.Net;
using System.IO;

namespace FlightSimulator
{
    delegate void PropertyEvent(double value);
    class InfoServer
    {
        //Make InfoServer Singelton
        private static InfoServer m_Instance = null;
        public static InfoServer Instance
        {
            get
            {
                if (m_Instance == null)
                {
                    m_Instance = new InfoServer();
                }
                return m_Instance;
            }
        }

        //Events for pops when Lat and Lon changed
        public event PropertyEvent LatChanged;
        public event PropertyEvent LonChanged;

        private TcpClient client;
        private TcpListener server;
        private bool serverOpen;

        //Constructor
        public InfoServer()
        {
            serverOpen = false;
        }

        public void connect()
        {
            //Reload settings
            ApplicationSettingsModel.Instance.ReloadSettings();

            new Thread(() =>
            {
                try
                {
                    //Create tcp listener and start it
                    server = new TcpListener(IPAddress.Parse(ApplicationSettingsModel.Instance.FlightServerIP), ApplicationSettingsModel.Instance.FlightInfoPort);
                    server.Start();
                    serverOpen = true;

                    //While untill the simulator connect to the server
                    while (true)
                    {
                        client = server.AcceptTcpClient();

                        StreamReader reader = new StreamReader(client.GetStream(), Encoding.ASCII);

                        //While conncted, read the information
                        while(client.Connected)
                        {
                            filter(reader);
                        }
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }).Start();
        }

        public void disconnect()
        {
            //If exit and connect close
            if(client != null && client.Connected)
            {
                client.Close();
            }
            
            //If connect, close
            if(serverOpen)
            {
                server.Stop();
            }
        }

        public void filter(StreamReader reader)
        {
            string line;
            //Read from buffer and parse the 2 first values (as lat and lon)
            while((line = reader.ReadLine()) != null)
            {
                string[] values = line.Split(',');
                LonChanged?.Invoke(double.Parse(values[0]));
                LatChanged?.Invoke(double.Parse(values[1]));
            }
        }


    }
}
