using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FlightSimulator.Model
{
    class AutoPilotModel
    {
        private Client cl = new Client();
        public void SendCommand(string commands)
        {
            //Foreach command in the textbox, split to new string
            List<string> commandList = new List<string>(commands.Split(new string[] { Environment.NewLine }, StringSplitOptions.None));
            for (int i = 0; i < commandList.Capacity; i++)
            {
                //add newline in the end of each command
                commandList[i] += Environment.NewLine;
            }
            cl.sendCommands(commandList);
        }
    }
}
