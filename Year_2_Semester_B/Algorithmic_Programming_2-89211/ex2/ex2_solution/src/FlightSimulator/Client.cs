using FlightSimulator.Model;
using FlightSimulator.Model.Interface;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;

namespace FlightSimulator
{
    class Client
    {
        private TcpClient client;
        private Queue<string> queueCommands;
        //Consructor
        public Client()
        {
            queueCommands = new Queue<string>();
        }

        public void addCommand(string command)
        {
            //If queue is empty
            if(queueCommands.Count == 0)
            {
                //Enter command to the commands
                queueCommands.Enqueue(command);
                //Send the command
                sendCommandsNoSleep();
            }
            //There others commands in the queue
            else
            {
                //Only enter the command to the queue
                queueCommands.Enqueue(command);
            }
        }

        public void sendCommands(List<string> commands)
        {
            //Load settings
            ApplicationSettingsModel.Instance.ReloadSettings();
            new Thread(() =>
            {
                try
                {
                    //Create tcp client the connect
                    client = new TcpClient(ApplicationSettingsModel.Instance.FlightServerIP.ToString(), ApplicationSettingsModel.Instance.FlightCommandPort);
                    StreamWriter writer = new StreamWriter(client.GetStream(), Encoding.ASCII);

                    //Foreach command - send it and wait 2 seconds
                    foreach (string command in commands)
                    {
                        writer.Write(command);
                        writer.Flush();
                        Thread.Sleep(2000);

                    }
                    //Close client
                    client.Close();
                }
                catch(Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }).Start();

        }

        public void sendCommandsNoSleep()
        {
            new Thread(() =>
            {
                //Load settings
                ApplicationSettingsModel.Instance.ReloadSettings();
                try
                {
                    //Create tcp client the connect
                    client = new TcpClient(ApplicationSettingsModel.Instance.FlightServerIP.ToString(), ApplicationSettingsModel.Instance.FlightCommandPort);
                    StreamWriter writer = new StreamWriter(client.GetStream(), Encoding.ASCII);
                    writer.Write(queueCommands.Peek());
                    writer.Flush();
                    client.Close();
                    queueCommands.Dequeue();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
                finally
                {
                    //If the queue is not empty - send the next command
                    if (queueCommands.Count != 0)
                    {
                        sendCommandsNoSleep();
                    }
                }
            }).Start();
        }
    }
}
