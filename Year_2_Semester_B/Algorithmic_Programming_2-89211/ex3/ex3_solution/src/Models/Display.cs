using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Web;
using System.Xml;


namespace EX3.Models
{
    public class Display
    {
        private static Display instance = null;
        public Queue<Data> MyQueue { get; set; }
        private Display() { }
        public string Str { get; set; }
        public int Port { get; set; }
        public bool isIp { get; set; }

        public string File { get; set; }
        public static Display Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new Display();
                }
                return instance;
            }
        }
        public Data readData()
        {
            string[] parts = Str.Split('.');
            if (isIp)
            {

                return readFromSimulator();
            }
            else
            {
                return readFromFile();
            }
        }
        //this function creates a new tcp client evry time it called, maybe we will prefer to create it once in the creation of the class
        private Data readFromSimulator()
        {
            TcpClient client = new TcpClient(Str, Port);
            StreamWriter writer = new StreamWriter(client.GetStream(), Encoding.ASCII);
            StreamReader reader = new StreamReader(client.GetStream(), Encoding.ASCII);

            writer.Write("get /position/longitude-deg\r\n");
            writer.Flush();
            string lon = reader.ReadLine().Split(new string[] { "'" }, StringSplitOptions.None)[1];


            writer.Write("get /position/latitude-deg\r\n");
            writer.Flush();
            string lat = reader.ReadLine().Split(new string[] { "'" }, StringSplitOptions.None)[1];

            writer.Write("get /controls/engines/current-engine/throttle\r\n");
            writer.Flush();
            string throttle = reader.ReadLine().Split(new string[] { "'" }, StringSplitOptions.None)[1];

            writer.Write("get /controls/flight/rudder\r\n");
            writer.Flush();
            string rudder = reader.ReadLine().Split(new string[] { "'" }, StringSplitOptions.None)[1];


            Data d = new Data((int)(Double.Parse(lon)), (int)(Double.Parse(lat)),Double.Parse(throttle), Double.Parse(rudder));
            //Close client
            client.Close();
            return d;
        }

        private Data readFromFile()
        {
            string path = HttpContext.Current.Server.MapPath(String.Format("~/files/{0}", Str + ".txt"));
            if(MyQueue == null)
            {
                string[] lines = System.IO.File.ReadAllLines(path);        // reading all the lines of the file
                MyQueue = new Queue<Data>();
                foreach (string line in lines) {
                    string[] par = line.Split(' ');
                    MyQueue.Enqueue(new Data(int.Parse(par[0]), int.Parse(par[1]), double.Parse(par[2]), double.Parse(par[3])));
                }
            }
            return MyQueue.Dequeue();
        }
    }
}