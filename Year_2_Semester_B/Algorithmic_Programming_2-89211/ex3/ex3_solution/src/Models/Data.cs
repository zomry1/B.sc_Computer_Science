using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml;

namespace EX3.Models
{
    public class Data
    {
        public Data(int lon, int lat,double throttle, double rudder)
        {
            Lon = lon;
            Lat = lat;
            Throttle = throttle;
            Rudder = rudder;
        }
        public int Lon { get; set; }
        public int Lat { get; set; }
        public double Throttle { get; set; }
        public double Rudder { get; set; }
        public void ToXml(XmlWriter writer)
        {
            writer.WriteStartElement("Data");
            writer.WriteElementString("Lon", this.Lon.ToString());
            writer.WriteElementString("Lat", this.Lat.ToString());
            writer.WriteElementString("Throttle", this.Throttle.ToString());
            writer.WriteElementString("Rudder", this.Rudder.ToString());
            writer.WriteEndElement();
        }
    }
}