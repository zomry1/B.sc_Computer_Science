using EX3.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.Mvc;
using System.Xml;

namespace EX3.Controllers
{
    public class FirstController : Controller
    {
        // GET: First
        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult save(string ip, string port, int hz, int length, string file)
        {
            //Create new file for saving
            string path = Server.MapPath("~/files/" + file + ".txt");
            if(System.IO.File.Exists(path))
            {
                System.IO.File.Delete(path);
            }
            Display.Instance.MyQueue = null;
            Display.Instance.Str = ip;
            Display.Instance.Port = int.Parse(port);
            Display.Instance.isIp = true;
            Display.Instance.File = file;
            Session["HZ"] = hz;
            Session["duration"] = length;
            return View();
        }

        [HttpGet]
        public ActionResult display(string ip, string port, int time = 0)
        {
            Display.Instance.MyQueue = null;
            string[] parts = ip.Split('.');
            int a;
            Display.Instance.Str = ip;
            Display.Instance.Port = int.Parse(port);
            if (parts.Length == 4 && int.TryParse(parts[0], out a) && int.TryParse(parts[1], out a)
                    && int.TryParse(parts[2], out a) && int.TryParse(parts[3], out a))
            {
                Display.Instance.isIp = true;
                Session["HZ"] = time;
            }
            else
            {
                Display.Instance.isIp = false;
                Session["HZ"] = int.Parse(port);
            }
            return View();
        }

    

        [HttpPost]
        public string GetData(string save = null)
        {
            try
            {
                Data data = Display.Instance.readData();
                if (save == null)
                {
                    return ToXml(data);
                }
                return ToXml(data, Display.Instance.File);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        private string ToXml(Data data, string filename = null)
        {
            //Initiate XML stuff
            StringBuilder sb = new StringBuilder();
            XmlWriterSettings settings = new XmlWriterSettings();
            XmlWriter writer = XmlWriter.Create(sb, settings);

            writer.WriteStartDocument();

            data.ToXml(writer);

            writer.WriteEndDocument();
            writer.Flush();
            if (filename != null)
            {
                using (System.IO.StreamWriter file = new System.IO.StreamWriter(Server.MapPath("~/files/" + filename+".txt"), true))
                {
                    file.WriteLine(data.Lon + " " + data.Lat + " " + data.Throttle + " " + data.Rudder);
                }
            }
            return sb.ToString();
        }




    }
}